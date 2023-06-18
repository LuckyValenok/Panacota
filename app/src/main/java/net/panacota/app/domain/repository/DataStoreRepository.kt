package net.panacota.app.domain.repository

import android.content.Context
import javax.inject.Inject

class DataStoreRepository @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun readData(key: String): String? = prefs.getString(key, null)

    fun saveData(key: String, value: String?) = prefs.edit().putString(key, value).apply()

    companion object {
        const val SHARED_PREF_NAME = "PANACOTA_PREF"
    }
}