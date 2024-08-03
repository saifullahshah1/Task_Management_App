package com.revoio.taskmanagementapp.tma.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/** Get Activity */
fun Context.getActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

/** Log Function */
fun Any?.debug(tag: String = "Default_Tag"){
    Log.d(tag, "$this")
}