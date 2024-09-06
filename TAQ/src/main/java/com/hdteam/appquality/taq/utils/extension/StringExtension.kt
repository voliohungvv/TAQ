package com.hdteam.appquality.taq.utils.extension

import android.util.Base64

/***
Created by HungVV
Created at 09:01/06-09-2024
 ***/


fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

fun String.fromBase64(): String {
    return String(Base64.decode(this.toByteArray(Charsets.UTF_8), Base64.NO_WRAP))
}