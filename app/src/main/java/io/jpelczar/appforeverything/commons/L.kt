package io.jpelczar.appforeverything.commons

import android.util.Log
import io.jpelczar.appforeverything.core.App


object L {

    @JvmStatic var enabled = true

    fun d(any: Any?) {
        d(App.TAG, any)
    }

    fun d(tag: String, any: Any?) {
        if (enabled)
            Log.d(tag, java.lang.String.valueOf(any))
    }

    fun e(any: Any?) {
        d(App.TAG, any)
    }

    fun e(tag: String, any: Any?) {
        if (enabled)
            Log.d(tag, java.lang.String.valueOf(any))
    }

    fun i(any: Any?) {
        d(App.TAG, any)
    }

    fun i(tag: String, any: Any?) {
        if (enabled)
            Log.d(tag, java.lang.String.valueOf(any))
    }

}