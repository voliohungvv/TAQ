package com.hdteam.appquality.taq.tracking.screen

import android.app.Activity
import android.app.Application
import android.health.connect.datatypes.AppInfo
import android.os.Bundle
import android.util.Log
import com.hdteam.appquality.taq.data.local.model.LogLocal
import com.hdteam.appquality.taq.di.DataModule
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.tracking.TAQ
import com.hdteam.appquality.taq.utils.util.InfoDevice

/***
Create by HungVV
Create at 16:36/11-09-2024
 ***/
private const val TAG = "MyActivityLifecycleCallbacks"

class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        TAQ.setCurrentActivity(p0)
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityCreated")
    }

    override fun onActivityStarted(p0: Activity) {
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityStarted")
    }

    override fun onActivityResumed(p0: Activity) {
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityResumed")
    }

    override fun onActivityPaused(p0: Activity) {
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityPaused")
    }

    override fun onActivityStopped(p0: Activity) {
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityStopped")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(p0: Activity) {
        TAQ.setCurrentActivity(null)
        ProviderInstance.logLocalRepo.insertInfoActivity(p0, "onActivityDestroyed")
    }
}