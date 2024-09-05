package com.hdteam.appquality.taq.utils.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal object FormatUtils {
    @SuppressLint("DefaultLocale")
    fun formatBytes(bytes: Long): String {
        if (bytes >= 1073741824) { // 1 GB
            val gb = bytes.toDouble() / (1024.0 * 1024.0 * 1024.0)
            return String.format("%.2f GB", gb, Locale.US)
        } else {
            val mb = bytes.toDouble() / (1024.0 * 1024.0)
            return String.format("%.2f MB", mb, Locale.US)
        }
    }

    fun formatTime(timeInMillis: Long): String {
        try {
            val date = Date(timeInMillis)
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss z", Locale.getDefault())
            return format.format(date)
        } catch (e: Exception) {
            return "Not formatted ${timeInMillis} to time"
        }
    }

}