package com.hdteam.appquality.taq.data.local

import android.content.Context
import android.content.SharedPreferences

/***
Create by HungVV
Create at 17:33/11-09-2024
 ***/
private const val TAG = "SharePref"

internal object SharePref {
    private var sharedPrefs: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreferences {
        synchronized(this){
            return sharedPrefs ?: context.getSharedPreferences(sharedPrefsFileName, Context.MODE_PRIVATE).also {
                sharedPrefs = it
            }
        }
    }

    const val sharedPrefsFileName = "shared-preferences-hd-taq"

}