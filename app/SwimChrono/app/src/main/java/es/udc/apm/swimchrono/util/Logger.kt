package es.udc.apm.swimchrono.util

import android.util.Log

object Logger {
    private const val TAG = "SwimCrhonoLogger"

    fun debug(className: String, message: String) {
        Log.d(TAG, "$className: $message")
    }

    fun info(className: String, message: String) {
        Log.i(TAG, "$className: $message")
    }

    fun warning(className: String, message: String) {
        Log.w(TAG, "$className: $message")
    }

    fun error(className: String, message: String) {
        Log.e(TAG, "$className: $message")
    }

    // Add more log levels if needed
}
