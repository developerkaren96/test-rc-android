package com.example.loginregistrationfragmentapp

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.net.Uri

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        // Например, установите полноэкранный режим или другой layout, если требуется
//        setContentView(R.layout.activity_video)
//
//        // Получаем параметры подключения из Intent (например, путь к .vv-файлу)
//        val vvUri: Uri? = intent.data
//        if (vvUri != null) {
//            // Читаем содержимое .vv-файла
//            val vvContent = contentResolver.openInputStream(vvUri)?.bufferedReader().use { it?.readText() } ?: ""
//            // Передаем этот файл в функцию инициализации SPICE
//            // Здесь предполагается, что MySpiceClient имеет метод initializeFromVv(content: String)
//            MySpiceClient.initializeFromVv(vvContent)
//        } else {
//            // Или, если вы передаете параметры напрямую:
//            val ip = intent.getStringExtra("host_ip") ?: ""
//            val vmId = intent.getStringExtra("vm_id") ?: ""
//            val password = intent.getStringExtra("password") ?: ""
//            // Сформируйте .vv-контент на лету
//            val vvContent = """
//                [virt-viewer]
//                type=spice
//                host=127.0.0.1
//                port=5900
//                password=$password
//                proxy=wss://$ip/ui/vms/$vmId/ws
//            """.trimIndent()
//            MySpiceClient.initializeFromVv(vvContent)
//        }
        // После инициализации, aSPICE установит соединение и отобразит стрим на своем Canvas.
    }
}