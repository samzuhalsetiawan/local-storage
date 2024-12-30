package com.samzuhalsetiawan.localstorage.util

import androidx.core.net.toUri
import com.samzuhalsetiawan.localstorage.file.File

private typealias JFile = java.io.File

internal fun JFile.toFile(): File {
    return File(
        id = absolutePath.hashCode().toLong(),
        uri = toUri(),
        displayName = name,
        absolutePath = absolutePath
    )
}