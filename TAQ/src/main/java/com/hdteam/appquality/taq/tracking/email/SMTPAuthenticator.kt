package com.hdteam.appquality.taq.tracking.email

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

/***
Created by HungVV
Created at 13:40/05-09-2024
 ***/


internal class SMTPAuthenticator(username: String?, password: String?) : Authenticator() {
    private val authentication =
        PasswordAuthentication(username, password)

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return authentication
    }
}