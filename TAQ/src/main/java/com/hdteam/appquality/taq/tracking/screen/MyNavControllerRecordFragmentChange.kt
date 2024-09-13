package com.hdteam.appquality.taq.tracking.screen

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination

/***
Created by HungVV
Created at 14:40/13-09-2024
 ***/

private const val TAG = "MyNavControllerRecordFragmentChange"


object MyNavControllerRecordFragmentChange {


    private val navChange by lazy {
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                Log.e(TAG, "onDestinationChanged: ${controller.currentDestination.toString()}")
            }
        }
    }

    fun register(navController: NavController, lifeCycle: Lifecycle) {
        lifeCycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_DESTROY -> {
                        navController.removeOnDestinationChangedListener(navChange)
                    }

                    Lifecycle.Event.ON_CREATE -> {
                        navController.addOnDestinationChangedListener(navChange)
                    }

                    else -> {

                    }
                }
            }
        })
    }


}