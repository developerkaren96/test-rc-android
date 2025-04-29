package com.example.loginregistrationfragmentapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginregistrationfragmentapp.vmlist.Deployment
import com.iiordanov.bVNC.Constants
import com.iiordanov.bVNC.RemoteCanvasActivity
import com.undatech.opaque.ConnectionSettings


private lateinit var logoutButton: ImageView
private lateinit var VmList: ListView
private lateinit var startButton: ImageButton
private lateinit var viewButton: ImageButton
private lateinit var suspendButton: ImageButton
private lateinit var pauseButton: ImageButton
private lateinit var resetButton: ImageButton
private lateinit var hardButton: ImageButton
private lateinit var shutdownGuestButton: ImageButton
private lateinit var resetGuestButton: ImageButton
private lateinit var currentVMName: TextView
private lateinit var guestOS: TextView
private lateinit var comatibility: TextView
private lateinit var DNSName: TextView
private lateinit var IPAddress: TextView
private lateinit var CPUusage: TextView
private lateinit var MemoryUsge: TextView
private lateinit var StorageUsage: TextView
private lateinit var VMIconSelected: ImageView


class DashboardActivity : AppCompatActivity() {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var hostIp: String

    @SuppressLint("DefaultLocale", "SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        VmList = findViewById(R.id.VMID)
        startButton = findViewById(R.id.vmStart)
        viewButton = findViewById(R.id.vmConnect)
        suspendButton = findViewById(R.id.vmSuspend)
        pauseButton = findViewById(R.id.vmPause)
        resetButton = findViewById(R.id.vmReset)
        hardButton = findViewById(R.id.vmHardStop)
        shutdownGuestButton = findViewById(R.id.vmShutDown)
        resetGuestButton = findViewById(R.id.vmResetGuest)
        currentVMName = findViewById(R.id.currentVM)
        guestOS = findViewById(R.id.GuestOS)
        comatibility = findViewById(R.id.Compatibility)
        DNSName = findViewById(R.id.DNSName)
        IPAddress = findViewById(R.id.IPaddress)
        CPUusage = findViewById(R.id.CPU)
        MemoryUsge = findViewById(R.id.Memory)
        StorageUsage = findViewById(R.id.Storage)
        VMIconSelected = findViewById(R.id.VmIconSelected)

        hostIp = intent.getStringExtra("host_ip") ?: ""
        if (hostIp == "") {
            Log.e("HOSTIP", "hostip пустой или null!")
        } else {
            Log.e("HOSTIP", "$hostIp")
        }


        val deployments = intent.getParcelableArrayListExtra<Deployment>("vm_list")
        Log.d("Deployment", "$deployments")
        if (deployments.isNullOrEmpty()) {
            Log.e("DetailActivity", "deployments пустой или null!")
            Toast.makeText(this, "Данные не получены!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Связывает список объектов с элементами интерфейса
        VmList.adapter = VmListAdapter(this, deployments) { selectedVm ->
            Toast.makeText(this, "Выбрана: ${selectedVm.name}", Toast.LENGTH_SHORT).show()
            viewModel.fetchSingleVm(selectedVm.deployment_name, hostIp, this)
            viewModel.startPeriodicRefresh(selectedVm.deployment_name, hostIp, this)
        }


        // Отображение данных выбранной ВМ
        viewModel.selectedVm.observe(this) { vm ->
            if (vm != null) {
                currentVMName.text = vm.name
                guestOS.text = vm.guest_os_family
                comatibility.text = vm.compatibility
                DNSName.text = vm.monitoring.guest_tools.dns_name
                IPAddress.text = vm.monitoring.guest_tools.ip
                CPUusage.text = String.format("%.2f", vm.monitoring.cpu.used_mhz) + " MHz"
                MemoryUsge.text = String.format("%.2f", vm.monitoring.memory.used_mb / 1024) + " GB"
                StorageUsage.text =
                    String.format("%.2f", vm.monitoring.storage.used_space_mb / 1024) + " GB"
                when (vm.monitoring.state) {
                    0 -> VMIconSelected.setImageResource(R.drawable.vmicon_error)
                    1 -> VMIconSelected.setImageResource(R.drawable.vmicon)
                    2 -> VMIconSelected.setImageResource(R.drawable.vmicon_on)
                    3 -> VMIconSelected.setImageResource(R.drawable.vmicon_suspended)
                    4 -> VMIconSelected.setImageResource(R.drawable.vmicon_error)
                    5 -> VMIconSelected.setImageResource(R.drawable.vmicon_warning)
                    6 -> VMIconSelected.setImageResource(R.drawable.vmicon_warning)
                    7 -> VMIconSelected.setImageResource(R.drawable.vmicon_suspended)
                    8 -> VMIconSelected.setImageResource(R.drawable.vmicon_warning)
                    9 -> VMIconSelected.setImageResource(R.drawable.vmicon_warning)
                    10 -> VMIconSelected.setImageResource(R.drawable.vmicon_warning)
                }
            }
        }


        viewButton.setOnClickListener {
            val connection = ConnectionSettings("tempConnection")
            connection.address = "10.10.101.8"
            connection.user = "root"
            connection.password = "P@ssw0rd"
            connection.port = 5901
            connection.isSslStrict = false
            connection.connectionTypeString = "SPICE";

            val intent = Intent(this, RemoteCanvasActivity::class.java)
            intent.putExtra(Constants.opaqueConnectionSettingsClassPath, connection)
            startActivity(intent)
        }

        // Кнопка включения
        startButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.startVm(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Запуск ВМ: ${vm.name}", Toast.LENGTH_SHORT).show()
            }
        }

        // Кнопка выключения
        suspendButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.stopVm(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Выключение ВМ: ${vm.name}", Toast.LENGTH_SHORT).show()
            }
        }

        hardButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.hardstopVm(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Жестко выключена ВМ: ${vm.name}", Toast.LENGTH_SHORT).show()
            }
        }

        pauseButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.pauseVm(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Пауза ВМ: ${vm.name}", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.resetVm(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Перезагрузка ВМ: ${vm.name}", Toast.LENGTH_SHORT).show()
            }
        }

        shutdownGuestButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.shutdownGuest(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Выключение гостевой ОС: ${vm.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        resetGuestButton.setOnClickListener {
            viewModel.selectedVm.value?.let { vm ->
                Log.d("BUTTON_TEST", "Выбрана VM: ${vm.name}")
                viewModel.restartGuest(vm.deployment_name, hostIp, this@DashboardActivity)
                Toast.makeText(this, "Перезаугрзка гостевой ОС: ${vm.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        }



        logoutButton = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
