package com.hdteam.appquality.taq.tracking.screen

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.tracking.TAQ

/***
Create by HungVV
Create at 16:36/11-09-2024
 ***/
private const val TAG = "MyActivityLifecycleCallbacks"

class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        TAQ.setCurrentActivity(p0)
        ProviderInstance.logLocalRepo.insertInfoActivity(
            p0,
            System.currentTimeMillis(),
            "onActivityCreated"
        )
    }

    override fun onActivityStarted(p0: Activity) {
//        ProviderInstance.logLocalRepo.insertInfoActivity(
//            p0,
//            System.currentTimeMillis(),
//            "onActivityStarted"
//        )
    }

    override fun onActivityResumed(p0: Activity) {
//        ProviderInstance.logLocalRepo.insertInfoActivity(
//            p0,
//            System.currentTimeMillis(),
//            "onActivityResumed"
//        )
    }

    override fun onActivityPaused(p0: Activity) {
//        ProviderInstance.logLocalRepo.insertInfoActivity(
//            p0,
//            System.currentTimeMillis(),
//            "onActivityPaused"
//        )
    }

    override fun onActivityStopped(p0: Activity) {
//        ProviderInstance.logLocalRepo.insertInfoActivity(
//            p0,
//            System.currentTimeMillis(),
//            "onActivityStopped"
//        )
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
//        ProviderInstance.logLocalRepo.insertInfoActivity(
//            p0,
//            System.currentTimeMillis(),
//            "onActivitySaveInstanceState"
//        )
    }

    override fun onActivityDestroyed(p0: Activity) {
        TAQ.setCurrentActivity(null)
        ProviderInstance.logLocalRepo.insertInfoActivity(
            p0,
            System.currentTimeMillis(),
            "onActivityDestroyed"
        )
    }
}