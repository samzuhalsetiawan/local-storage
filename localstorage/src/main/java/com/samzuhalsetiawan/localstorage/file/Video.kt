package com.samzuhalsetiawan.localstorage.file

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

data class Video(
    override val id: Long,
    override val uri: Uri,
    override val displayName: String,
    override val absolutePath: String,
    val duration: Long,
): File(id, uri, displayName, absolutePath) {
    companion object {
        internal val mediaStoreColumns = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.DURATION,
        )
    }
}

internal fun Cursor.toVideo(collection: Collection): Video {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID))
    return Video(
        id = id,
        uri = ContentUris.withAppendedId(collection.uri, id),
        displayName = getString(getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)),
        absolutePath = getString(getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA)),
        duration = getLong(getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)),
    )
}