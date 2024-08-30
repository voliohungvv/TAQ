package com.hdteam.appquality.taq.utils

import android.util.Log
import com.hdteam.appquality.taq.utils.util.EndCodeUtils

/***
Created by HungVV
Created at 14:48/30-08-2024
 ***/

private const val TAG = "Constant"

internal object Constant {
    var mailUser = "acirEDy7PAOo472GaVk94gcGFV9Yur8d4v0UdvKFIUw="
    var mailPass = "0Yt1xzuuHdlVPLnlfQ+wwVuAuGCmWU8OjLzhA6WSor8="

    const val mailSend = "HungVV@govo.tech"
    const val mailBack = "HungVV@govo12345"
    init {
//        val a = EndCodeUtils.encrypt(key, vec, mailPass)
//        val b = EndCodeUtils.encrypt(key, vec, mailUser)
//        Log.e(TAG, "${a}", )
//        Log.e(TAG, "${b}", )


        mailUser = EndCodeUtils.decrypt(mailSend, mailBack, mailUser)
        mailPass = EndCodeUtils.decrypt(mailSend, mailBack, mailPass)

//        Log.e(TAG, "${mailUser}", )
//        Log.e(TAG, "${mailPass}", )
    }
}