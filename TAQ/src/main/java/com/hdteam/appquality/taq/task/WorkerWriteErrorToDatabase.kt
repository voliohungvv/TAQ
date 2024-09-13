package com.hdteam.appquality.taq.task

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.hdteam.appquality.taq.di.ProviderInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

private const val TAG = "WorkerWriteErrorToDatab"

internal class WorkerWriteErrorToDatabase(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val throwMethodName = inputData.getString(KEY_METHOD_NAME) ?: ""
        val error = inputData.getString(KEY_ERROR) ?: ""
        val timeCreated = inputData.getLong(KEY_TIME_CREATE,0)
        return withContext(Dispatchers.IO) {
            ProviderInstance.logLocalRepo.insertInfoException(
                methodName = throwMethodName,
                timeCreated,
                error = error
            )
            Result.success()
        }
    }


    companion object {

        val KEY_METHOD_NAME: String = "KEY_METHOD_NAME"
        val KEY_ERROR: String = "KEY_ERROR"
        val KEY_TIME_CREATE: String = "KEY_TIME_CREATE"

        fun writeDB(context: Context, methodName: String,timeCreated: Long, error: String): UUID {
            val inputData = workDataOf(KEY_METHOD_NAME to methodName, KEY_ERROR to error, KEY_TIME_CREATE to timeCreated)

            val constraints: Constraints = Constraints
                .Builder().apply {
                }.build()

            val task: WorkRequest =
                OneTimeWorkRequestBuilder<WorkerWriteErrorToDatabase>()
                    .setInputData(inputData)
                    .setConstraints(constraints)
                    .setInitialDelay(0, TimeUnit.SECONDS)
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        WorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.SECONDS
                    )
                    .build()
            WorkManager.getInstance(context).enqueue(task)
            return task.id
        }
    }

}