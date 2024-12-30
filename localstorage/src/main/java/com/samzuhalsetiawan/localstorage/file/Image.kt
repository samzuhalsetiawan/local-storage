package com.samzuhalsetiawan.localstorage.file

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

data class Image(
    override val id: Long,
    override val uri: Uri,
    override val displayName: String,
    override val absolutePath: String,
): File(id, uri, displayName, absolutePath) {
    companion object {
        internal val mediaStoreColumns = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA,
        )
    }
}

internal fun Cursor.toImage(collection: Collection): Image {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
    return Image(
        id = id,
        uri = ContentUris.withAppendedId(collection.uri, id),
        displayName = getString(getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
        absolutePath = getString(getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)),
    )
}