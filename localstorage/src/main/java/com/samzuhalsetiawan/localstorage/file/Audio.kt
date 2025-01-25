package com.samzuhalsetiawan.localstorage.file

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

data class Audio(
    override val id: Long,
    override val uri: Uri,
    override val displayName: String,
    override val absolutePath: String,
    val title: String?,
    val artist: String?,
    val duration: Long,
    val album: Album?
): File(id, uri, displayName, absolutePath) {
    companion object {
        internal val mediaStoreColumns = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DURATION,
        ) + Album.mediaStoreColumns
    }
}

internal fun Cursor.toAudio(collection: Collection): Audio {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
    return Audio(
        id = id,
        uri = ContentUris.withAppendedId(collection.uri, id),
        displayName = getString(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)),
        title = getStringOrNull(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)),
        artist = getStringOrNull(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)),
        absolutePath = getString(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)),
        duration = getLong(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)),
        album = getLongOrNull(getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID))?.let { albumId ->
            Album(
                id = albumId,
                albumArtUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
            )
        },
    )
}

data class Album(
    val id: Long,
    val albumArtUri: Uri?
) {
    companion object {
        internal val mediaStoreColumns = arrayOf(
            MediaStore.Audio.AudioColumns.ALBUM_ID,
        )
    }
}