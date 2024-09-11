package com.hdteam.appquality.taq.tracking

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.tracking.error.MainUncaughtExceptionHandler
import com.hdteam.appquality.taq.tracking.screen.MyActivityLifecycleCallbacks
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

    fun init(application: Application) {
        this._application = application
        ProviderInstance.sharePref.setSessionOpenApp()
        val mainHandler = MainUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(mainHandler)
        application.registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
    }


    fun setCurrentActivity(activity: Activity?){
        _currentActivity = activity
    }
}