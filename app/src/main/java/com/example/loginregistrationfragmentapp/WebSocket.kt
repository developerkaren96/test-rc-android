//package com.example.loginregistrationfragmentapp
//
//import okhttp3.Response
//import okhttp3.WebSocket
//import okhttp3.WebSocketListener
//import okio.ByteString
//import android.util.Log
//
//class VmWebSocketListener(
//    private val onMessageReceived: (String) -> Unit
//) : WebSocketListener() {
//
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        Log.d("WebSocket", "Соединение установлено")
//        // При необходимости можно отправить сообщение на сервер:
//        // webSocket.send("Hello from client!")
//    }
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        Log.d("WebSocket", "onMessage: $text")
//        // Передаём текст в колбэк, чтобы потом распарсить
//        onMessageReceived(text)
//    }
//
//    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        Log.d("WebSocket", "onMessage (binary): ${bytes.hex()}")
//        // Если сервер присылает бинарные данные, обрабатывайте их тут
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        Log.d("WebSocket", "onClosing: code=$code, reason=$reason")
//        webSocket.close(1000, null)
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        Log.e("WebSocket", "onFailure: ${t.message}")
//    }
//}