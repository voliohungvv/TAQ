package com.hdteam.appquality.taq.tracking.error

import android.text.TextUtils
import android.util.Log
import com.hdteam.appquality.taq.di.ProviderInstance

/***
Create by HungVV
Create at 16:36/11-09-2024
 ***/
private const val TAG = "MainUncaughtExceptionHandler"

class MainUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {

    private val otherHandlers = mutableListOf<Thread.UncaughtExceptionHandler>()

    fun addHandler(handler: Thread.UncaughtExceptionHandler) {
        otherHandlers.add(handler)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        var rootTr: Throwable = throwable
        val exceptionType: String = throwable.javaClass.name

        var cause: String? = throwable.message

        var mThrowable: Throwable? = throwable
        while (mThrowable?.cause != null) {
            mThrowable = mThrowable.cause
            if (mThrowable?.stackTrace?.isNotEmpty() == true)
                rootTr = mThrowable
            if (!mThrowable?.message.isNullOrEmpty()) {
                cause = mThrowable?.message
            }
        }

        val throwClassName: String
        val throwMethodName: String
        val throwLineNumber: Int

        if (rootTr.stackTrace.isNotEmpty()) {
            val trace: StackTraceElement = rootTr.stackTrace[0]
            throwClassName = trace.className
            throwMethodName = trace.methodName
            throwLineNumber = trace.lineNumber
        } else {
            throwClassName = "unknown"
            throwMethodName = "unknown"
            throwLineNumber = 0
        }
        Log.e(TAG, "exceptionType: ${exceptionType}")
        Log.e(TAG, "throwClassName: ${throwClassName}")
        Log.e(TAG, "throwMethodName: ${throwMethodName}")
        Log.e(TAG, "throwLineNumber: ${throwLineNumber}")

        ProviderInstance.logLocalRepo.insertInfoException(
            methodName = throwMethodName,
            error = "exceptionType = ${exceptionType}," +
                    " throwClassName = ${throwClassName}," +
                    " throwLineNumber =  ${throwLineNumber}," +
                    " thread = ${thread.name}," +
                    " e = ${throwable.message}"
        )
        for (handler in otherHandlers) {
            handler.uncaughtException(thread, throwable)
        }
    }
}
