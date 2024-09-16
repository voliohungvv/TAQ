package com.hdteam.appquality.taq.di

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.hdteam.appquality.taq.data.local.SharePref
import com.hdteam.appquality.taq.data.repository.LogLocalRepositoryImpl
import com.hdteam.appquality.taq.tracking.TAQ

/***
Create by HungVV
Create at 17:24/11-09-2024
 ***/
private const val TAG = "ProviderInstance"

internal object ProviderInstance {
    val application: Application by lazy { TAQ.application }

    val activityManager by lazy { application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }

    val connectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    val appDatabase get() = DataModule.getInstanceDatabaseLog(appContext = application)

    val logLocalRepo by lazy { LogLocalRepositoryImpl(application, sharePref, appDatabase) }
    val sharePref by lazy { SharePref.getInstance(application) }
}