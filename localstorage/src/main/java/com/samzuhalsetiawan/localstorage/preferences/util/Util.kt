package com.samzuhalsetiawan.localstorage.preferences.util

import androidx.datastore.preferences.core.Preferences
import com.samzuhalsetiawan.localstorage.preferences.UnSupportedPreferencesType

internal fun Preferences.findKey(keyName: String): Preferences.Key<*>? {
    return  asMap().keys.find { it.name == keyName }
}

//@Suppress("UNCHECKED_CAST")
//internal fun <T: Any> Preferences.findKey(keyName: String): Preferences.Key<T>? {
//    val entry = asMap().entries.find { it.key.name == keyName }
//    val preferencesKey = when (val value = entry?.value) {
//        null -> return null
//        is Int,
//        is Long,
//        is Float,
//        is Double,
//        is Boolean,
//        is String,
//        is ByteArray -> entry.key
//        else -> throw UnSupportedPreferencesType(value::class.simpleName.toString())
//    }
//    return preferencesKey as Preferences.Key<T>
//}