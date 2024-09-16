package com.hdteam.appquality.taq.data.repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.hdteam.appquality.taq.data.local.AppDatabase
import com.hdteam.appquality.taq.data.local.model.LogLocal
import com.hdteam.appquality.taq.tracking.TAQ
import com.hdteam.appquality.taq.utils.extension.getSessionOpenApp
import com.hdteam.appquality.taq.utils.extension.getTypeNetworkConnect
import com.hdteam.appquality.taq.utils.util.FormatUtils
import com.hdteam.appquality.taq.utils.util.InfoDevice
import kotlinx.coroutines.CoroutineExceptionHandler
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
) : LogLocalRepository {

    private val coroutineException = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Write log with error : ${throwable.toString()}")
    }


    private fun executeInBackground(doWork: suspend () -> Unit) {
        if(TAQ.isEnabled){
            CoroutineScope(Dispatchers.IO + coroutineException).launch {
                doWork.invoke()
            }
        }
    }

    override fun insertInfoActivity(activity: Activity, timeCreate: Long, methodName: String) {

        executeInBackground {
            val logLocal = LogLocal(
                screenName = activity.javaClass.name,
                methodName = methodName,
                error = "",
                availableRam = InfoDevice.getRamInfoFormat(),
                typeConnected = getTypeNetworkConnect(),
                session = sharedPref.getSessionOpenApp().toString(),
                versionApp = InfoDevice.getVersionCode().toString(),
                createdAtRaw = System.currentTimeMillis().toString(),
                createdAt = FormatUtils.formatTime(System.currentTimeMillis())

            )
            appDatabase.localDao().insertAll(logLocal)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun insertInfoFragment(
        navController: NavController,
        navDestination: NavDestination,
        arguments: Bundle?
    ) {

        executeInBackground {
            val nameCurrentScreen =
                navController.currentDestination?.displayName ?: navDestination.toString()

            val logLocal = LogLocal(
                screenName = nameCurrentScreen,
                methodName = "OnDestinationChanged",
                error = "",
                availableRam = InfoDevice.getRamInfoFormat(),
                typeConnected = getTypeNetworkConnect(),
                session = sharedPref.getSessionOpenApp().toString(),
                versionApp = InfoDevice.getVersionCode().toString(),
                createdAtRaw = System.currentTimeMillis().toString(),
                createdAt = FormatUtils.formatTime(System.currentTimeMillis())
            )
            appDatabase.localDao().insertAll(logLocal)
        }
    }

    override fun insertInfoException(methodName: String, timeCreate: Long, error: String) {

        executeInBackground {
            val logLocal = LogLocal(
                screenName = TAQ.currentActivity?.javaClass?.name,
                methodName = methodName,
                error = error,
                availableRam = InfoDevice.getRamInfoFormat(),
                typeConnected = getTypeNetworkConnect(),
                session = sharedPref.getSessionOpenApp().toString(),
                versionApp = InfoDevice.getVersionCode().toString(),
                createdAtRaw = timeCreate.toString(),
                createdAt = FormatUtils.formatTime(timeCreate)

            )
            appDatabase.localDao().insertAll(logLocal)
        }
    }

    override fun deleteAllLog() {
        executeInBackground {
            appDatabase.localDao().deleteAll()
        }
    }
}