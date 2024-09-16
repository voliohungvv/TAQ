package com.hdteam.appquality.taq.task

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.hdteam.appquality.taq.model.GmailModel
import com.hdteam.appquality.taq.tracking.email.GmailSender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

/***
Created by HungVV
Created at 14:12/05-09-2024
 ***/
private const val TAG = "WorkerSendGmail"
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
                    Log.e(TAG, "doWork: ${e.toString()}", )
                    Result.failure()
                }
            } else {
                Log.e(TAG, "doWork: gmail is null", )
                Result.failure()
            }
        }
    }


    companion object {

        val KEY_GMAIL_DATA: String = "KEY_GMAIL_DATA"

        fun sendGmailWorker(
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
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
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
    }

}