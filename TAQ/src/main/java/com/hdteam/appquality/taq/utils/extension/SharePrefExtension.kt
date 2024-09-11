package com.hdteam.appquality.taq.utils.extension

import android.content.SharedPreferences

/***
Create by HungVV
Create at 17:38/11-09-2024
 ***/
private const val TAG = "SharePrefExtension"


private val keySessionOpenApp = "keySessionOpenApp"

fun SharedPreferences.getSessionOpenApp(): Int {
   return getInt(keySessionOpenApp, 0)
}

fun SharedPreferences.setSessionOpenApp(){
    edit().apply {
        putInt(keySessionOpenApp, getSessionOpenApp()+1)
        apply()
    }
}