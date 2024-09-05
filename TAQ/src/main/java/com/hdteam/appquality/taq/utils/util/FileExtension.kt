package com.hdteam.appquality.taq.utils.util

import android.os.Build
import android.webkit.MimeTypeMap
import java.io.File
import java.nio.file.Files

/***
Created by HungVV
Created at 11:56/05-09-2024
 ***/


internal fun File.contentType(): String = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Files.probeContentType(this.toPath())
    } else {
        val extension: String = MimeTypeMap.getFileExtensionFromUrl(this.absolutePath)
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "application/octet-stream"
    }
} catch (e: Exception) {
    "application/octet-stream"
}