package com.hdteam.appquality.taq.utils.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import com.hdteam.appquality.taq.di.ProviderInstance

/***
Created by HungVV
Created at 09:32/05-09-2024
 ***/


internal fun isInternetAvailable(): Boolean {
    return try {
        val connectivityManager = ProviderInstance.connectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } catch (e: Exception) {
        true
    }
}


internal fun getTypeNetworkConnect(): String {
    return try {
        val connectivityManager = ProviderInstance.connectivityManager
        val nw = connectivityManager.activeNetwork ?: return "NO DETECTION"
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return "NO DETECTION"
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "TRANSPORT_WIFI"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "TRANSPORT_CELLULAR"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "TRANSPORT_ETHERNET"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> "TRANSPORT_BLUETOOTH"
            else -> "NO DETECTION"
        }
    } catch (e: Exception) {
        "NO DETECTION"
    }
}

internal fun Any.delayHandler(durationInMillis: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper())
        .postDelayed({ block.invoke() }, durationInMillis)
}