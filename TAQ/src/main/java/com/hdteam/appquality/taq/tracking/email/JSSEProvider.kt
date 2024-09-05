package com.hdteam.appquality.taq.tracking.email

import java.security.AccessController
import java.security.PrivilegedAction
import java.security.Provider

/***
Created by HungVV
Created at 13:39/05-09-2024
 ***/


internal class JSSEProvider : Provider("HarmonyJSSE", 1.0, "Harmony JSSE Provider") {
    init {
        AccessController.doPrivileged(PrivilegedAction<Void?> {
            put(
                "SSLContext.TLS",
                "org.apache.harmony.xnet.provider.jsse.SSLContextImpl"
            )
            put("Alg.Alias.SSLContext.TLSv1", "TLS")
            put(
                "KeyManagerFactory.X509",
                "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl"
            )
            put(
                "TrustManagerFactory.X509",
                "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl"
            )
            null
        })
    }
}