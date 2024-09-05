package com.hdteam.appquality.taq.utils.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/***
Created by HungVV
Created at 09:32/05-09-2024
 ***/


internal fun Context?.isInternetAvailable(): Boolean {
    return try {
        val connectivityManager =
            this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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


internal fun Context?.getTypeNetworkConnect(): String {
    return try {
        val connectivityManager =
            this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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