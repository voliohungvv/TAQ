package com.hdteam.appquality.taq.di

import android.app.Application
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
    val appDatabase by lazy { DataModule.getInstance(appContext = application) }
    val logLocalRepo by lazy { LogLocalRepositoryImpl(application, sharePref,appDatabase) }
    val sharePref by lazy { SharePref.getInstance(application) }
}