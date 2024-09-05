package com.hdteam.appquality.taq.tracking.email

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.hdteam.appquality.taq.model.GmailModel
import com.hdteam.appquality.taq.model.GmailState
import com.hdteam.appquality.taq.task.WorkerSendGmail
import com.hdteam.appquality.taq.utils.Constant
import com.hdteam.appquality.taq.utils.util.contentType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.security.Security
import java.util.Properties
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


/***
Created by HungVV
Created at 13:37/30-08-2024
 ***/


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
        props["mail.smtp.host"] = mailhost
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.ssl.trust"] = mailhost

//        props.setProperty("mail.smtp.quitwait", "false")
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

    fun sendMailNormalSync(
        gmail: GmailModel
    ) {
        val message = MimeMessage(session)
//        val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))
//        message.dataHandler = handler

        message.subject = gmail.subject

        val emailContent: Multipart = MimeMultipart()

        // HTML Text
        if (gmail.body.isNotEmpty()) {
            val htmlPart = MimeBodyPart()
            htmlPart.setHeader("Content-Type", "text/html")
            htmlPart.setContent(gmail.body, "text/html")
            emailContent.addBodyPart(htmlPart)
        }

        // Attachment
        gmail.filePathAttach.toSet().forEach { path ->
            val file = File(path)
            if (file.exists()) {
                val attachmentPart = MimeBodyPart()
                attachmentPart.setHeader("Content-Type", file.contentType())
                attachmentPart.setHeader(
                    "Content-Disposition",
                    "attachment; filename=\"" + file.getName() + "\""
                )
                attachmentPart.attachFile(file) // Specify the file path
                emailContent.addBodyPart(attachmentPart)
            }
        }

        // Set content
        message.setContent(emailContent)
        if (gmail.recipients.indexOf(",") > 0) {
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(gmail.recipients)
            )
        } else {
            message.setRecipient(Message.RecipientType.TO, InternetAddress(gmail.recipients))
        }
        message.saveChanges()

        Transport.send(message)
    }

    fun sendMailNormalAsync(
        gmail: GmailModel,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            CoroutineScope(Dispatchers.Main).launch {
                onFailure.invoke(Exception(throwable))
            }
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            sendMailNormalSync(gmail)
            withContext(Dispatchers.Main) {
                onSuccess.invoke()
            }
        }
    }


    fun sendGmailEnqueue(
        context: Context,
        gmailModel: GmailModel
    ): UUID {

        val inputData = workDataOf(WorkerSendGmail.KEY_GMAIL_DATA to gmailModel.toJson())

        val constraints: Constraints = Constraints
            .Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }.build()

        val task: WorkRequest =
            OneTimeWorkRequestBuilder<WorkerSendGmail>()
                .setInputData(inputData)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    30,
                    TimeUnit.SECONDS
                )
                .build()
        WorkManager.getInstance(context).enqueue(task)
        return task.id
    }

    fun registerStateGmailWithIDGmail(context: Context, idGmail: UUID) =
        WorkManager.getInstance(context).getWorkInfoByIdFlow(idGmail).map {
            when (it.state) {
                WorkInfo.State.SUCCEEDED -> {
                    GmailState.SUCCESS
                }

                WorkInfo.State.FAILED -> {
                    GmailState.ERROR
                }

                WorkInfo.State.RUNNING -> {
                    GmailState.IN_PROGRESS
                }

                WorkInfo.State.ENQUEUED -> {
                    GmailState.ENQUEUED
                }

                WorkInfo.State.BLOCKED -> {
                    GmailState.BLOCKED
                }

                WorkInfo.State.CANCELLED -> {
                    GmailState.CANCELLED
                }
            }
        }


    fun cancelGmailWhenRetry(context: Context, idGmail: UUID) {
        WorkManager.getInstance(context).cancelWorkById(idGmail)
    }
}