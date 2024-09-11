package com.hdteam.appquality.taq.data.repository

import android.app.Activity
import androidx.room.Insert
import com.hdteam.appquality.taq.data.local.model.LogLocal

/***
Create by HungVV
Create at 17:17/11-09-2024
 ***/
private const val TAG = "LogLocalRepository"

internal interface LogLocalRepository {

     fun insertInfoActivity(activity: Activity,methodName: String)
     fun insertInfoFragment(logLocal: LogLocal)
     fun insertInfoException(methodName:String,error: String)

}