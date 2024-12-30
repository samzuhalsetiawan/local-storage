package com.samzuhalsetiawan.localstorage.file

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

open class File(
    open val id: Long,
    open val uri: Uri,
    open val displayName: String,
    open val absolutePath: String,
) {
    companion object {
        internal val mediaStoreColumns = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
        )
    }
}

internal fun Cursor.toFile(collection: Collection): File {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
    return File(
        id = id,
        uri = ContentUris.withAppendedId(collection.uri, id),
        displayName = getString(getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)),
        absolutePath = getString(getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)),
    )
}