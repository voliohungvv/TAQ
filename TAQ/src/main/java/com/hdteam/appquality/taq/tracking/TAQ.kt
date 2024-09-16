package com.hdteam.appquality.taq.tracking

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.hdteam.appquality.taq.data.local.AppDatabase
import com.hdteam.appquality.taq.di.DataModule
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.tracking.error.MainUncaughtExceptionHandler
import com.hdteam.appquality.taq.tracking.screen.MyActivityLifecycleCallbacks
import com.hdteam.appquality.taq.tracking.screen.MyNavControllerRecordFragmentChange
import com.hdteam.appquality.taq.utils.extension.setSessionOpenApp

/***
Create by HungVV
Create at 16:31/11-09-2024
 ***/
private const val TAG = "TAQ"

@SuppressLint("StaticFieldLeak")
object TAQ {

    private var _application: Application? = null
    val application: Application get() = _application!!

    private var _currentActivity: Activity? = null
    val currentActivity get() = _currentActivity

    private var _isEnabled: Boolean = true
    val isEnabled: Boolean get() = _isEnabled

    fun init(application: Application, isNewSession: Boolean = true, isEnabled: Boolean = true) {
        _isEnabled = isEnabled
        this._application = application
        if (isEnabled) {
            if (isNewSession) {
                ProviderInstance.sharePref.setSessionOpenApp()
            }
            val mainHandler = MainUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(mainHandler)
            application.registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
        }
    }


    internal fun setCurrentActivity(activity: Activity?) {
        _currentActivity = activity
    }

    fun setNavController(navController: NavController, lifeCycle: Lifecycle) {
        MyNavControllerRecordFragmentChange.register(navController, lifeCycle)
    }

    fun deleteAllLog() {
        ProviderInstance.logLocalRepo.deleteAllLog()
    }


    fun getPathDatabaseLog() = application.getDatabasePath(AppDatabase.appDatabaseName)

    fun deleteDatabaseLog() {
        DataModule.closeDatabaseLog()
        val fileDatabase = getPathDatabaseLog()
        if (fileDatabase.exists()) {
            fileDatabase.delete()
        }
    }
}