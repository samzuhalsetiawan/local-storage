package com.samzuhalsetiawan.localstorage.file.util

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.webkit.MimeTypeMap

internal fun <T> Cursor?.map(action: (cursor: Cursor) -> T): List<T> {
    val list = mutableListOf<T>()
    forEachRecord {
        list.add(action(it))
    }
    return list
}

internal fun Cursor?.forEachRecord(action: (cursor: Cursor) -> Unit) {
    this?.use {
        while (moveToNext()) {
            action(this)
        }
    }
}

internal fun getFileMimeType(
    contentResolver: ContentResolver,
    uri: Uri,
    displayName: String
): String? {
    return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        contentResolver.getType(uri)
    } else {
        val fileExtension = displayName.substringAfterLast(".", "").ifBlank { return null }
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase())
    }
}