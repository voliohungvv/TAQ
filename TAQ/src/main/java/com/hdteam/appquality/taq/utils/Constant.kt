package com.hdteam.appquality.taq.utils

import android.util.Log
import com.hdteam.appquality.taq.utils.extension.fromBase64
import com.hdteam.appquality.taq.utils.extension.toBase64
import com.hdteam.appquality.taq.utils.util.EndCodeUtils

/***
Created by HungVV
Created at 14:48/30-08-2024
 ***/


internal object Constant {
    var mailUser = "acirEDy7PAOo472GaVk94gcGFV9Yur8d4v0UdvKFIUw="
    var mailPass = "1U6gg89BtQ2kdg8DSqv7s7hxRnZYqI10z8P4m3+TnwY="

    var mailSend = "HungVV@govo.tech"

    init {
//        val a = EndCodeUtils.encrypt(mailSend, mailBack, "oxravletqztyebgo")
//        val b = EndCodeUtils.encrypt(mailSend, mailBack, mailUser)
//        Log.e(TAG, "${a}", )
//        Log.e(TAG, "${b}", )
        Log.e("XXX", " ${EndCodeUtils.mailBack.toBase64()}", )

        mailUser = EndCodeUtils.decrypt(mailSend, EndCodeUtils.mailBack.fromBase64(), mailUser)
//        mailUser = "emailsenderhd@gmail.com"
        mailPass = EndCodeUtils.decrypt(mailSend, EndCodeUtils.mailBack.fromBase64(), mailPass)
//        mailPass = "oxravletqztyebgo"

//        Log.e(TAG, "${mailUser}", )
//        Log.e(TAG, "${mailPass}", )
    }
}