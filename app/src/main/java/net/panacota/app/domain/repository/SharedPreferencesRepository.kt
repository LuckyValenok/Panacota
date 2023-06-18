package net.panacota.app.domain.repository

import android.content.Context
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun readData(key: String): String? = prefs.getString(key, null)

    fun saveData(key: String, value: String?) = prefs.edit().putString(key, value).apply()

    fun getAllPairs(): List<Pair<String, Any?>> =
        prefs.all.filter { entry -> entry.value != null }.map { entry -> Pair(entry.key, entry.value) }

    fun clearAll() = prefs.edit().clear().apply()

    companion object {
        const val SHARED_PREF_NAME = "PANACOTA_PREF"
    }
}