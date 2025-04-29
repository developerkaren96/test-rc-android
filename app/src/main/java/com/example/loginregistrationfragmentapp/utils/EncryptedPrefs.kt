package com.example.loginregistrationfragmentapp.utils

import android.content.Context
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.loginregistrationfragmentapp.data.AccountInfo

//
//class EncryptedPrefs(context: Context) {
//
//    private val masterKey = MasterKey.Builder(context)
//        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//        .build()
//
//    private val encryptedSharedPrefs = EncryptedSharedPreferences.create(
//        context,
//        "secure_prefs",
//        masterKey,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
//
//    /**
//     * Сохранить список аккаунтов (в JSON-формате).
//     */
//    fun saveAccounts(accounts: List<AccountInfo>) {
//        val json = Gson().toJson(accounts)
//        encryptedSharedPrefs.edit()
//            .putString("accounts", json)
//            .apply()
//    }
//
//    /**
//     * Загрузить все аккаунты (JSON → List<AccountInfo>).
//     */
//    fun loadAccounts(): List<AccountInfo> {
//        val json = encryptedSharedPrefs.getString("accounts", null) ?: return emptyList()
//        val type = object : TypeToken<List<AccountInfo>>() {}.type
//        return Gson().fromJson(json, type)
//    }
//
//    /**
//     * Добавить или обновить аккаунт (по host/ip).
//     */
//    fun addOrUpdateAccount(account: AccountInfo) {
//        val current = loadAccounts().toMutableList()
//        // удаляем старую запись, если username совпадает
//        current.removeAll { it.ip == account.ip }
//        current.add(account)
//        saveAccounts(current)
//    }
//    // Сохранение токена
//    fun saveToken(token: String) {
//        encryptedSharedPrefs.edit()
//            .putString("auth_token", token)
//            .apply()
//    }
//
//    // Получение токена
//    fun getToken(): String? {
//        return encryptedSharedPrefs.getString("auth_token", null)
//    }
//
//    // Очистка токена
//    fun clearToken() {
//        encryptedSharedPrefs.edit()
//            .remove("auth_token")
//            .apply()
//    }
//
//}
class PlainPrefs(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("plain_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPrefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return sharedPrefs.getString("auth_token", null)
    }

    fun clearToken() {
        sharedPrefs.edit().remove("auth_token").apply()
    }

    fun loadAccounts(): List<AccountInfo> {
        // Получаем JSON-строку из обычных SharedPreferences
        val json = sharedPrefs.getString("accounts", null) ?: return emptyList()
        // Определяем тип данных для Gson
        val type = object : TypeToken<List<AccountInfo>>() {}.type
        // Парсим JSON в список аккаунтов
        return Gson().fromJson(json, type)
    }
}
