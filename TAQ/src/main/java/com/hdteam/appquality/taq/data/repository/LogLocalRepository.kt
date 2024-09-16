package com.hdteam.appquality.taq.data.repository

import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.room.Insert
import com.hdteam.appquality.taq.data.local.model.LogLocal

/***
Create by HungVV
Create at 17:17/11-09-2024
 ***/
private const val TAG = "LogLocalRepository"

internal interface LogLocalRepository {

     fun insertInfoActivity(activity: Activity,timeCreate: Long,methodName: String)
     fun insertInfoFragment(navController: NavController, navDestination: NavDestination, arguments: Bundle?)
     fun insertInfoException(methodName:String,timeCreate: Long,error: String)
     fun deleteAllLog()

}