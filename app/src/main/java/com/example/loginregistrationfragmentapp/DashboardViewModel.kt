package com.example.loginregistrationfragmentapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.loginregistrationfragmentapp.utils.EncryptedPrefs
import com.example.loginregistrationfragmentapp.utils.PlainPrefs
import com.example.loginregistrationfragmentapp.vmlist.Deployment
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

//TODO заменить статичный ip на ip подключенного сервера, подключить защищенное соединение
class DashboardViewModel : ViewModel() {
    private val _vmList = MutableLiveData<List<Deployment>>()
    val vmList: LiveData<List<Deployment>> = _vmList

    private val _selectedVm = MutableLiveData<Deployment?>()
    val selectedVm: LiveData<Deployment?> = _selectedVm

    private var refreshJob: Job? = null


    // Обновляет информацию раз в 5 сек
    fun startPeriodicRefresh(vmId: String,host: String, context: Context) {
        // Останавливаем предыдущий опрос, если был
        refreshJob?.cancel()

        // Запускаем новый
        refreshJob = viewModelScope.launch {
            while (isActive) {
                fetchSingleVm(vmId, host ,context)
                delay(5000)
            }
        }
    }

    // делаем запрос для получения информации от выбранной ВМ
    fun fetchSingleVm(vmId: String,host: String, context: Context) {
        val prefs = PlainPrefs(context)
        val accessToken = prefs.getToken() ?: run {
            Toast.makeText(context, "Требуется авторизация", Toast.LENGTH_SHORT).show()
            return
        }

        // эндпоинт для конкретной ВМ:
        val apiUrl = "https://$host/ui/vms/$vmId"

        val request = Request.Builder()
            .url(apiUrl)
            .header("Authorization", "Bearer $accessToken")
            .build()

        val httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(host)

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CALL","Ошибка при запросе ВМ $vmId: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("CALL","Ошибка выполнения запроса: Код ${it.code}")
                    } else {
                        val responseBody = it.body?.string()
                        Log.d("CALL","Данные по ВМ $vmId: $responseBody")

                        // Парсим JSON в Deployment
                        val updatedVm: Deployment? = parseSingleVm(responseBody)
                        // Кладём результат в _selectedVm (обновим UI)
                        _selectedVm.postValue(updatedVm)
                    }
                }
            }
        })
    }
    // Парсим данные выбраной ВМ
    private fun parseSingleVm(json: String?): Deployment? {
        if (json.isNullOrEmpty()) return null
        return try {
            val gson = Gson()
            gson.fromJson(json, Deployment::class.java)
        } catch (e: Exception) {
            Log.e("CALL", "Ошибка парсинга: ${e.message}")
            null
        }
    }

    // Шаблон отправки запросов на сервер по нажатию кнопок
    private fun sendVmCommand(
        command: String,
        vmId: String,
        host: String,
        context: Context
    ) {
        val prefs = PlainPrefs(context)
        val accessToken = prefs.getToken() ?: run {
            Toast.makeText(context, "Требуется авторизация", Toast.LENGTH_SHORT).show()
            return
        }

        val apiUrl = "https://$host/ui/tasks"

        // Формируем JSON, подставляя нужный метод
        val commandJson = """
        {
            "method": "$command",
            "params": {
                "target": "$vmId",
                "args": ""
            }
        }
    """.trimIndent()

        val requestBody = commandJson.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $accessToken")
            .build()

        val httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(host)

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CALL","Ошибка при отправке запроса: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("CALL","Ошибка выполнения запроса: Код ${it.code}")
                    } else {
                        Log.e("CALL","Команда '$command' успешно выполнена! Ответ сервера: ${it.body?.string()}")
                    }
                }
            }
        })
        Log.d("VM", "Отправлена команда '$command' для VM $vmId")
    }

    fun selectVm(vm: Deployment) {
        _selectedVm.value = vm
    }

    // Включение ВМ
    fun startVm(vmId: String, host: String,context: Context) {
        sendVmCommand("vm.power_on", vmId,  host, context)
    }

    // Выключение ВМ
    fun stopVm(vmId: String, host: String,context: Context) {
        sendVmCommand("vm.power_off", vmId,host, context)
    }

    //
    fun pauseVm(vmId: String,host: String,context: Context) {
        sendVmCommand("vm.power_suspend", vmId, host, context)
    }

    fun hardstopVm(vmId: String,host: String, context: Context) {
        sendVmCommand("vm.hard_stop", vmId,host, context)
    }

    fun resetVm(vmId: String,host: String, context: Context) {
        sendVmCommand("vm.reset", vmId,host, context)
    }

    fun shutdownGuest(vmId: String,host: String, context: Context) {
        sendVmCommand("vm.shutdown_guest", vmId,host, context)
    }

    fun restartGuest(vmId: String,host: String, context: Context) {
        sendVmCommand("vm.restart_guest", vmId,host, context)
    }

    //
    fun monitoringVm(vmId: String) {
        // Логика мониторинга ВМ
        Log.d("VM", "Мониторинг VM $vmId")
    }
}
