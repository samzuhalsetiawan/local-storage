package com.samzuhalsetiawan.localstorage

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.samzuhalsetiawan.localstorage.file.Audio
import com.samzuhalsetiawan.localstorage.file.File
import com.samzuhalsetiawan.localstorage.file.Image
import com.samzuhalsetiawan.localstorage.file.Video
import com.samzuhalsetiawan.localstorage.file.FileManager
import com.samzuhalsetiawan.localstorage.preferences.PreferencesManager
import com.samzuhalsetiawan.localstorage.util.toFile
import kotlinx.coroutines.flow.Flow

interface LocalStorage {

    /**
     * Query audio files from the internal storage of this app.
     * @param musicOnly whether to only query music files.
     * @return list of [Audio] files.
     */
    suspend fun queryInternalAudioFiles(musicOnly: Boolean = false): List<Audio>

    /**
     * Query audio files from the external storage of this device.
     * @return list of [Audio] files.
     */
    suspend fun queryExternalAudioFiles(musicOnly: Boolean = false): List<Audio>

    /**
     * Query video files from the internal storage of this app.
     * @return list of [Video] files.
     */
    suspend fun queryInternalVideoFiles(): List<Video>

    /**
     * Query video files from the external storage of this device.
     * @return list of [Video] files.
     */
    suspend fun queryExternalVideoFiles(): List<Video>

    /**
     * Query image files from the internal storage of this app.
     * @return list of [Image] files.
     */
    suspend fun queryInternalImageFiles(): List<Image>

    /**
     * Query image files from the external storage of this device.
     * @return list of [Image] files.
     */
    suspend fun queryExternalImageFiles(): List<Image>

    /**
     * Query all files from the internal storage of this app.
     * @return list of [File].
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun queryAllInternalFiles(): List<File>

    /**
     * Query all files from the external storage of this device.
     * @return list of [File].
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun queryAllExternalFiles(): List<File>

    /**
     * Query all files from the external storage of this device.
     * this function intended to be used on older device that running API 28 and below.
     * for API 29 and above, we highly recommend to use [queryAllExternalFiles] instead.
     * @return list of [File].
     */
    suspend fun queryAllExternalFilesLegacy(): List<File>
    suspend fun <T: Any> savePreferences(key: String, value: T)
    suspend fun removePreferences(key: String)
    suspend fun <T: Any> getPreferences(key: String): T?
    fun preferencesAsFlow(): Flow<Map<String, Any>>
}

fun LocalStorage(applicationContext: Context): LocalStorage = LocalStorageImpl(applicationContext)

internal class LocalStorageImpl(
    applicationContext: Context
): LocalStorage {

    private val fileManager = FileManager(applicationContext)
    private val preferencesManager = PreferencesManager(applicationContext)

    override suspend fun queryInternalAudioFiles(musicOnly: Boolean): List<Audio> {
        return fileManager.queryInternalAudioFiles(musicOnly)
    }

    override suspend fun queryExternalAudioFiles(musicOnly: Boolean): List<Audio> {
        return fileManager.queryExternalAudioFiles(musicOnly)
    }

    override suspend fun queryInternalVideoFiles(): List<Video> {
        return fileManager.queryInternalVideoFiles()
    }

    override suspend fun queryExternalVideoFiles(): List<Video> {
        return fileManager.queryExternalVideoFiles()
    }

    override suspend fun queryInternalImageFiles(): List<Image> {
        return fileManager.queryInternalImageFiles()
    }

    override suspend fun queryExternalImageFiles(): List<Image> {
        return fileManager.queryExternalImageFiles()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun queryAllInternalFiles(): List<File> {
        return fileManager.queryAllInternalFiles()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun queryAllExternalFiles(): List<File> {
        return fileManager.queryAllExternalFiles()
    }

    override suspend fun queryAllExternalFilesLegacy(): List<File> {
        return Environment
            .getExternalStorageDirectory()
            .listFiles()
            ?.map { it.toFile() } ?: emptyList()
    }

    override suspend fun <T : Any> savePreferences(key: String, value: T) {
        return preferencesManager.savePreferences(key, value)
    }

    override suspend fun removePreferences(key: String) {
        return preferencesManager.removePreferences(key)
    }

    override suspend fun <T : Any> getPreferences(key: String): T? {
        return preferencesManager.getPreferences(key)
    }

    override fun preferencesAsFlow(): Flow<Map<String, Any>> {
        return preferencesManager.preferencesAsFlow()
    }
}