package com.hdteam.appquality.taq.utils.util

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Process
import java.io.File


/***
Create by HungVV
Create at 16:01/11-09-2024
 ***/
private const val TAG = "AppStateUtils"

object  AppStateUtils {

    fun isIntentAvailable(context: Context?, intent: Intent?): Boolean {
        if (context == null || intent == null) return false
        val packageManager = context.applicationContext.packageManager
        val list = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return list.size > 0
    }

    fun isAppInBackground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return true
        for (appProcess in appProcesses) {
            if (appProcess.processName == context.packageName) {
                return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND
            }
        }
        return false
    }



    fun isMainProcess(context: Context): Boolean {
        try {
            val am = (context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            val processInfo = am.runningAppProcesses
            val mainProcessName = context.packageName
            val myPid = Process.myPid()
            for (info in processInfo) {
                if (info.pid == myPid && mainProcessName == info.processName) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    private fun clearAppData(dir: File?): Boolean {
        if (dir == null || !dir.isDirectory || !dir.exists()) return false
        val files = dir.listFiles()
        val length = files?.size ?: 0
        for (i in 0 until length) {
            val file = files?.get(i) ?: continue
            if (file.isFile && file.exists()) {
                val result = file.delete()
                continue
            }
            if (file.isDirectory && file.exists()) {
                clearAppData(file)
            }
        }
        return true
    }


}