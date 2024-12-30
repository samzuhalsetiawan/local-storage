package com.samzuhalsetiawan.localstorage.file

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

internal sealed interface Collection {
    val type: Type
    val scope: Scope
    val uri: Uri
    val projection: Array<String>
    enum class Type {
        AUDIO, VIDEO, IMAGE, FILE
    }
    enum class Scope {
        INTERNAL, EXTERNAL
    }
}

internal data object AudioInternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.INTERNAL
    override val type: Collection.Type = Collection.Type.AUDIO
    override val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
    override val projection: Array<String> = Audio.mediaStoreColumns
}

internal data object AudioExternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.EXTERNAL
    override val type: Collection.Type = Collection.Type.AUDIO
    override val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    override val projection: Array<String> = Audio.mediaStoreColumns
}

internal data object VideoInternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.INTERNAL
    override val type: Collection.Type = Collection.Type.VIDEO
    override val uri: Uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
    override val projection: Array<String> = Video.mediaStoreColumns
}

internal data object VideoExternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.EXTERNAL
    override val type: Collection.Type = Collection.Type.VIDEO
    override val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    override val projection: Array<String> = Video.mediaStoreColumns
}

internal data object ImageInternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.INTERNAL
    override val type: Collection.Type = Collection.Type.IMAGE
    override val uri: Uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
    override val projection: Array<String> = Image.mediaStoreColumns
}

internal data object ImageExternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.EXTERNAL
    override val type: Collection.Type = Collection.Type.IMAGE
    override val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    override val projection: Array<String> = Image.mediaStoreColumns
}

@RequiresApi(Build.VERSION_CODES.Q)
internal data object FileInternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.INTERNAL
    override val type: Collection.Type = Collection.Type.FILE
    override val uri: Uri = MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL)
    override val projection: Array<String> = File.mediaStoreColumns
}

@RequiresApi(Build.VERSION_CODES.Q)
internal data object FileExternal: Collection {
    override val scope: Collection.Scope = Collection.Scope.EXTERNAL
    override val type: Collection.Type = Collection.Type.FILE
    override val uri: Uri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
    override val projection: Array<String> = File.mediaStoreColumns
}



