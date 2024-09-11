package com.hdteam.appquality.taq.data.repository

import android.app.Activity
import android.content.Context
import com.hdteam.appquality.taq.data.local.AppDatabase
import com.hdteam.appquality.taq.data.local.model.LogLocal
import com.hdteam.appquality.taq.utils.util.InfoDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/***
Create by HungVV
Create at 17:19/11-09-2024
 ***/
private const val TAG = "LogLocalRepositoryImpl"

internal class LogLocalRepositoryImpl(val context: Context,val  appDatabase: AppDatabase) :
    LogLocalRepository {

    override fun insertInfoActivity(actuvity: Activity) {
        val logLocal = LogLocal(
            screenName = actvity.javaClass.simpleName,
            methodName = "onActivityCreated",
            error = "",
            availableRam = InfoDevice.getRamInfoFormat()
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

    override fun insertInfoException(logLocal: LogLocal) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.localDao().insertAll(logLocal)
        }
    }
}