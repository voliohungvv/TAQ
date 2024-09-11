package com.hdteam.appquality.taq.data.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.hdteam.appquality.taq.data.local.AppDatabase
import com.hdteam.appquality.taq.data.local.model.LogLocal
import com.hdteam.appquality.taq.tracking.TAQ
import com.hdteam.appquality.taq.utils.extension.getSessionOpenApp
import com.hdteam.appquality.taq.utils.extension.getTypeNetworkConnect
import com.hdteam.appquality.taq.utils.util.FormatUtils
import com.hdteam.appquality.taq.utils.util.InfoDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/***
Create by HungVV
Create at 17:19/11-09-2024
 ***/
private const val TAG = "LogLocalRepositoryImpl"

internal class LogLocalRepositoryImpl(
    val context: Context,
    val sharedPref: SharedPreferences,
    val appDatabase: AppDatabase
) :
    LogLocalRepository {

    override fun insertInfoActivity(activity: Activity, methodName: String) {
        val logLocal = LogLocal(
            screenName = activity.javaClass.name,
            methodName = methodName,
            error = "",
            availableRam = InfoDevice.getRamInfoFormat(context),
            typeConnected = context.getTypeNetworkConnect(),
            session = sharedPref.getSessionOpenApp().toString(),
            versionApp = InfoDevice.getVersionCode(context).toString(),
            createdAt = FormatUtils.formatTime(System.currentTimeMillis())

        )
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.localDao().insertAll(logLocal)
        }
    }

    override fun insertInfoFragment(logLocal: LogLocal) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.localDao().insertAll(logLocal)
        }
    }

    override fun insertInfoException(methodName:String,error: String) {
        val logLocal = LogLocal(
            screenName = TAQ.currentActivity?.javaClass?.name,
            methodName = methodName,
            error = error,
            availableRam = InfoDevice.getRamInfoFormat(context),
            typeConnected = context.getTypeNetworkConnect(),
            session = sharedPref.getSessionOpenApp().toString(),
            versionApp = InfoDevice.getVersionCode(context).toString(),
            createdAt = FormatUtils.formatTime(System.currentTimeMillis())

        )
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.localDao().insertAll(logLocal)
        }
    }
}