package com.example.loginregistrationfragmentapp

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

//Todo: НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ В ВЕРСИИ ПРОД!!! НУЖНО ЗАМЕНИТЬ!!!
object UnsafeOkHttpClient {

    // Регулярное выражение для проверки IP-адресов, начинающихся с 10.10.101.
    private val trustedIpRegex = Regex("^10\\.10\\.101\\.")

    // Метод для создания небезопасного OkHttpClient (отключает проверку SSL)
    fun getUnsafeOkHttpClient(host: String): OkHttpClient {
        return if (isTrustedHost(host)) {
            // Если хост начинается с 10.10.101., создаем небезопасный клиент
            createUnsafeClient()
        } else {
            // Иначе создаем стандартный безопасный клиент
            OkHttpClient.Builder().build()
        }
    }

    // Проверяем, начинается ли хост с 10.10.101.
    private fun isTrustedHost(host: String): Boolean {
        return trustedIpRegex.containsMatchIn(host)
    }

    // Создаем небезопасный клиент (отключает проверку SSL)
    private fun createUnsafeClient(): OkHttpClient {
        return try {
            // Создаем TrustManager, который доверяет всем сертификатам (не проверяет их)
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Инициализируем SSLContext с нашим TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Создаем SSLSocketFactory с нашим SSLContext
            val sslSocketFactory = sslContext.socketFactory

            // Создаем и настраиваем OkHttpClient
            OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager) // Используем наш SSLSocketFactory
                .hostnameVerifier { _, _ -> true } // Отключаем проверку имени хоста
                .build()
        } catch (e: Exception) {
            throw RuntimeException("Ошибка при создании UnsafeOkHttpClient", e)
        }
    }
}