package com.example.loginregistrationfragmentapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
//import com.example.loginregistrationfragmentapp.utils.EncryptedPrefs
import android.content.Intent
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregistrationfragmentapp.utils.PlainPrefs
import com.example.loginregistrationfragmentapp.vmlist.Deployment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: PlainPrefs
    private lateinit var authHost: TextInputEditText
    private lateinit var authUsername: TextInputEditText
    private lateinit var authPassword: TextInputEditText
    private lateinit var hostLayout: TextInputLayout
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var rememberCheckBox: CheckBox
    private var deployments: List<Deployment> = emptyList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = PlainPrefs(this)
        authHost = findViewById(R.id.auth_host)
        authUsername = findViewById(R.id.auth_username)
        authPassword = findViewById(R.id.auth_password)
        loginButton = findViewById(R.id.login)
        progressBar = findViewById(R.id.progressBar)
        rememberCheckBox = findViewById(R.id.remember)
        hostLayout = findViewById(R.id.host_layout)
        usernameLayout = findViewById(R.id.username_layout)
        passwordLayout = findViewById(R.id.password_layout)

        authHost.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                showAccountsDialog()
            }
        }
        val host = authHost.text.toString().trim()
        val username = authUsername.text.toString().trim()
        val password = authPassword.text.toString().trim()

        loginButton.setOnClickListener {
            validateInputs()
            sendLoginRequest(username,password,host)
            setLoadingState(true)
        }

        // Add text watchers to clear errors on input change
        authHost.addTextChangedListener(createTextWatcher(hostLayout, getString(R.string.error_ip_required)))
        authUsername.addTextChangedListener(createTextWatcher(usernameLayout, getString(R.string.error_username_required)))
        authPassword.addTextChangedListener(createTextWatcher(passwordLayout, getString(R.string.error_password_required)))
    }

    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            loginButton.isEnabled = false
            loginButton.visibility = View.INVISIBLE
            loginButton.text = ""
            progressBar.visibility = View.VISIBLE
        } else {
            loginButton.isEnabled = true
            loginButton.visibility = View.VISIBLE
            loginButton.text = getString(R.string.auth_login)
            progressBar.visibility = View.GONE
        }
    }

    fun fetchVMs(host: String, accessToken: String) {
        val url = "https://$host/ui/vms" // Формируем URL для получения VMs

        // Создаем запрос с токеном в заголовке Authorization
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken") // Добавляем токен в заголовок
            .get() // Используем GET-запрос
            .build()

        // Используем UnsafeOkHttpClient для отключения проверки SSL
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient(host)

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    println(jsonResponse)
                    if (jsonResponse != null) {
                        val gson = Gson()
                        val type = object : TypeToken<List<Deployment>>() {}.type
                        val deployments: List<Deployment> = gson.fromJson(jsonResponse, type)
                        val firstVM = deployments[0]
                        if (deployments.isNotEmpty()) {
                            runOnUiThread{
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java).apply {
                                    putParcelableArrayListExtra("vm_list", ArrayList(deployments))
                                    putExtra("host_ip", host)
                                }
                                startActivity(intent)
                            }
                        }
                        runOnUiThread {
                            deployments.forEach { deployment ->
                                Log.e("DEPLOYMENT", "VM: ${deployment.name}, UUID: ${deployment.uuid}")
                            }

                        }
                    }
                    // Обрабатываем ответ (например, отображаем список VMs)
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Список VMs: $jsonResponse", Toast.LENGTH_LONG).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Ошибка: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    private fun showAccountsDialog() {
        val accounts = prefs.loadAccounts()
        if (accounts.isEmpty()) return

        // Создаём список хостов
        val hosts = accounts.map { it.ip }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Выберите аккаунт")
            .setItems(hosts) { _, which ->
                val selected = accounts[which]
                authHost.setText(selected.ip)
                authUsername.setText(selected.username)
                authPassword.setText(selected.password)
            }
            .show()
    }

    private fun createTextWatcher(layout: TextInputLayout, error: String): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    layout.error = if (s.isNotEmpty()) null else error
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun validateInputs() {
        val host = authHost.text.toString().trim()
        val username = authUsername.text.toString().trim()
        val password = authPassword.text.toString().trim()

        var isValid = true

        // Validate IP
        if (TextUtils.isEmpty(host)) {
            hostLayout.error = getString(R.string.error_ip_required)
            isValid = false
        } else if (!Patterns.IP_ADDRESS.matcher(host).matches()) {
            hostLayout.error = getString(R.string.error_invalid_ip)
            isValid = false
        } else {
            hostLayout.error = null
        }

        // Validate Username
        if (TextUtils.isEmpty(username)) {
            usernameLayout.error = getString(R.string.error_username_required)
            isValid = false
        } else {
            usernameLayout.error = null
        }

        // Validate Password
        if (TextUtils.isEmpty(password)) {
            passwordLayout.error = getString(R.string.error_password_required)
            isValid = false
        } else if (password.length < 6) {
            passwordLayout.error = getString(R.string.error_password_too_short)
            isValid = false
        } else {
            passwordLayout.error = null
        }

        if (isValid) {
            // Proceed with login
            sendLoginRequest(host, username, password)
        }
    }

    private fun sendLoginRequest(host: String, username: String, password: String) {
        val url = "https://$host/ui/login" // Формируем URL

        // Создаем JSON-тело запроса
        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        // Создаем запрос
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // Используем UnsafeOkHttpClient для отключения проверки SSL
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient(host)

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    println("Ошибка сети: ${e.message}")
                    Toast.makeText(this@MainActivity, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val accessToken = jsonResponse.optString("access_token")
                    prefs.saveToken(accessToken)

                    fetchVMs(host, accessToken)

                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Access Token сохранен", Toast.LENGTH_LONG).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Ошибка: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
