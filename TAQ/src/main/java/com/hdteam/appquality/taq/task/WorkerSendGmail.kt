package com.hdteam.appquality.taq.task

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.hdteam.appquality.taq.model.GmailModel
import com.hdteam.appquality.taq.tracking.email.GmailSender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/***
Created by HungVV
Created at 14:12/05-09-2024
 ***/

internal class WorkerSendGmail(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val gmail = GmailModel.fromJson(inputData.getString(KEY_GMAIL_DATA))
        return withContext(Dispatchers.IO) {
            if (gmail != null) {
                try {
                    GmailSender.sendMailNormalSync(gmail)
                    Result.success()
                } catch (e: Exception) {
                    Result.failure()
                }
            } else {
                Result.failure()
            }
        }
    }


    companion object {

        val KEY_GMAIL_DATA: String = "KEY_GMAIL_DATA"

    }

}