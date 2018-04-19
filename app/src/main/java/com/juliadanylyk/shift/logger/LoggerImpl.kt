package com.juliadanylyk.shift.logger

import android.util.Log
import com.juliadanylyk.shift.BuildConfig

class LoggerImpl : Logger {
    companion object {
        private const val LOG_TAG = "Shift"
        private val LOGS_ON = BuildConfig.DEBUG
    }

    override fun d(message: String) {
        if (LOGS_ON) {
            val t = Throwable()
            val elements = t.stackTrace

            val callerClassName = elements[1].fileName
            Log.d(LOG_TAG, "[$callerClassName] $message")
        }
    }

    override fun w(message: String) {
        if (LOGS_ON) {
            val t = Throwable()
            val elements = t.stackTrace

            val callerClassName = elements[1].fileName
            Log.w(LOG_TAG, "[$callerClassName] $message")
        }
    }

    override fun w(message: String, cause: Throwable) {
        if (LOGS_ON) {
            val t = Throwable()
            val elements = t.stackTrace

            val callerClassName = elements[1].fileName
            Log.w(LOG_TAG, "[$callerClassName] $message", cause)
        }
    }

    override fun e(message: String) {
        if (LOGS_ON) {
            val t = Throwable()
            val elements = t.stackTrace

            val callerClassName = elements[1].fileName
            Log.e(LOG_TAG, "[$callerClassName] $message")
        }
    }

    override fun e(message: String, cause: Throwable) {
        if (LOGS_ON) {
            val t = Throwable()
            val elements = t.stackTrace

            val callerClassName = elements[1].fileName
            Log.e(LOG_TAG, "[$callerClassName] $message", cause)
        }
    }
}