package com.hdteam.appquality.taq.tracking.screen

import android.app.Activity
import android.app.Application
import android.health.connect.datatypes.AppInfo
import android.os.Bundle
import android.util.Log
import com.hdteam.appquality.taq.data.local.model.LogLocal
import com.hdteam.appquality.taq.di.DataModule
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.utils.util.InfoDevice

/***
Create by HungVV
Create at 16:36/11-09-2024
 ***/
private const val TAG = "MyActivityLifecycleCallbacks"

class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Log.e(TAG, "onActivityCreated: ${p0.javaClass.simpleName}")
        ProviderInstance.logLocalRepo.insertInfoActivity(p0)
    }

    override fun onActivityStarted(p0: Activity) {
        Log.e(TAG, "onActivityStarted: ${p0.javaClass.simpleName}")
    }

    override fun onActivityResumed(p0: Activity) {
        Log.e(TAG, "onActivityResumed: ${p0.javaClass.simpleName}")
    }

    override fun onActivityPaused(p0: Activity) {
        Log.e(TAG, "onActivityPaused: ${p0.javaClass.simpleName}")
    }

    override fun onActivityStopped(p0: Activity) {
        Log.e(TAG, "onActivityStopped: ${p0.javaClass.simpleName}")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        Log.e(TAG, "onActivitySaveInstanceState: ${p0.javaClass.simpleName}")
    }

    override fun onActivityDestroyed(p0: Activity) {
        Log.e(TAG, "onActivityDestroyed: ${p0.javaClass.simpleName}")
    }
}