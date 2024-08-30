package com.hdteam.appquality.taq.tracking.email

import com.hdteam.appquality.taq.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.Security
import java.util.Properties
import javax.activation.DataHandler
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/***
Created by HungVV
Created at 13:37/30-08-2024
 ***/

private const val TAG = "GmailSender"

object GmailSender {

    private var user: String = "sender@gmail.com"
    private var password = ""

    private const val mailhost = "smtp.gmail.com"
    private var session: Session? = null

    private val properties by lazy {
        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", mailhost)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")
        return@lazy props
    }

    init {
        Security.addProvider(JSSEProvider())
        this.user = Constant.mailUser
        this.password = Constant.mailPass
        session =
            Session.getDefaultInstance(properties, SMTPAuthenticator(this.user, this.password))
    }

    fun setEmailSender(user: String, password: String) {
        this.user = user
        this.password = password
        session =
            Session.getDefaultInstance(properties, SMTPAuthenticator(this.user, this.password))
    }

    fun sendMailNormalSync(subject: String?, body: String, recipients: String) {
        val message = MimeMessage(session)
        val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))
        message.subject = subject
        message.dataHandler = handler
        if (recipients.indexOf(",") > 0) {
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipients)
            )
        } else {
            message.setRecipient(Message.RecipientType.TO, InternetAddress(recipients))
        }
        Transport.send(message)

    }

    fun sendMailNormalAsync(
        subject: String?,
        body: String,
        recipients: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            CoroutineScope(Dispatchers.Main).launch {
                onFailure.invoke(Exception(throwable))
            }
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            sendMailNormalSync(subject, body, recipients)
            withContext(Dispatchers.Main) {
                onSuccess.invoke()
            }
        }
    }
}