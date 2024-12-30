package com.samzuhalsetiawan.localstorage.file

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.samzuhalsetiawan.localstorage.file.util.map

internal interface FileManager {
    fun queryInternalAudioFiles(
        musicOnly: Boolean = false
    ): List<Audio>
    fun queryExternalAudioFiles(
        musicOnly: Boolean = false
    ): List<Audio>
    fun queryInternalVideoFiles(): List<Video>
    fun queryExternalVideoFiles(): List<Video>
    fun queryInternalImageFiles(): List<Image>
    fun queryExternalImageFiles(): List<Image>
    @RequiresApi(Build.VERSION_CODES.Q)
    fun queryAllInternalFiles(): List<File>
    @RequiresApi(Build.VERSION_CODES.Q)
    fun queryAllExternalFiles(): List<File>
}

private class FileManagerImpl(
    private val applicationContext: Context
): FileManager {

    private val contentResolver = applicationContext.contentResolver

    private fun queryAudioFiles(
        collection: Collection,
        musicOnly: Boolean = false
    ): List<Audio> {
        val collectionUri = collection.uri
        val projection = if (musicOnly) {
            collection.projection + arrayOf(MediaStore.Audio.Media.IS_MUSIC)
        } else {
            collection.projection
        }
        val selection = if (musicOnly) {
            "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        } else {
            null
        }
        val selectionArgs = if (musicOnly) {
            arrayOf("1")
        } else {
            null
        }
        val sortOrder = null
        return contentResolver
            .query(collectionUri, projection, selection, selectionArgs, sortOrder)
            .map { it.toAudio(collection) }
    }

    private fun queryVideoFiles(collection: Collection): List<Video> {
        val collectionUri = collection.uri
        val projection = collection.projection
        val selection = null
        val selectionArgs = null
        val sortOrder = null
        return contentResolver
            .query(collectionUri, projection, selection, selectionArgs, sortOrder)
            .map { it.toVideo(collection) }
    }

    private fun queryImageFiles(collection: Collection): List<Image> {
        val collectionUri = collection.uri
        val projection = collection.projection
        val selection = null
        val selectionArgs = null
        val sortOrder = null
        return contentResolver
            .query(collectionUri, projection, selection, selectionArgs, sortOrder)
            .map { it.toImage(collection) }
    }

    private fun queryFiles(collection: Collection): List<File> {
        val collectionUri = collection.uri
        val projection = collection.projection
        val selection = null
        val selectionArgs = null
        val sortOrder = null
        return contentResolver
            .query(collectionUri, projection, selection, selectionArgs, sortOrder)
            .map { it.toFile(collection) }
    }

    override fun queryInternalAudioFiles(musicOnly: Boolean): List<Audio> {
        return queryAudioFiles(AudioInternal, musicOnly)
    }

    override fun queryExternalAudioFiles(musicOnly: Boolean): List<Audio> {
        return queryAudioFiles(AudioExternal, musicOnly)
    }

    override fun queryInternalVideoFiles(): List<Video> {
        return queryVideoFiles(VideoInternal)
    }

    override fun queryExternalVideoFiles(): List<Video> {
        return queryVideoFiles(VideoExternal)
    }

    override fun queryInternalImageFiles(): List<Image> {
        return queryImageFiles(ImageInternal)
    }

    override fun queryExternalImageFiles(): List<Image> {
        return queryImageFiles(ImageExternal)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun queryAllInternalFiles(): List<File> {
        return queryFiles(FileInternal)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun queryAllExternalFiles(): List<File> {
        return queryFiles(FileExternal)
    }
}

internal fun FileManager(
    applicationContext: Context
): FileManager {
    return FileManagerImpl(
        applicationContext = applicationContext
    )
}