package com.samzuhalsetiawan.localstorage.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samzuhalsetiawan.localstorage.preferences.util.findKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStorePreferences: DataStore<Preferences> by preferencesDataStore(name = "preferences")

internal interface PreferencesManager {
    suspend fun <T: Any> savePreferences(key: String, value: T)
    suspend fun removePreferences(key: String)
    suspend fun <T: Any> getPreferences(key: String): T?
    fun preferencesAsFlow(): Flow<Map<String, Any>>
}

internal class PreferencesManagerImpl(
    private val applicationContext: Context
): PreferencesManager {

    private val dataStorePreferences = applicationContext.dataStorePreferences

    override suspend fun <T: Any> savePreferences(key: String, value: T) {
        dataStorePreferences.edit { preferences ->
            when (value) {
                is Int -> preferences[intPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is String -> preferences[stringPreferencesKey(key)] = value
                is ByteArray -> preferences[byteArrayPreferencesKey(key)] = value
                else -> throw UnSupportedPreferencesType(value::class.simpleName.toString())
            }
        }
    }

    override suspend fun removePreferences(key: String) {
        dataStorePreferences.edit { preferences ->
            val preferencesKey = preferences.findKey(key)
            preferencesKey?.let { preferences.remove(it) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T: Any> getPreferences(key: String): T? {
        val preferences = dataStorePreferences.data.first()
        val entry = preferences.asMap().entries.find { it.key.name == key }
        return entry?.value as? T
    }

    override fun preferencesAsFlow(): Flow<Map<String, Any>> {
        return dataStorePreferences.data.map { preferences ->
            preferences.asMap().mapKeys { it.key.name }
        }
    }
}

internal fun PreferencesManager(
    applicationContext: Context
): PreferencesManager {
    return PreferencesManagerImpl(
        applicationContext = applicationContext
    )
}