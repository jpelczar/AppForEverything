package io.jpelczar.core.commons

import android.util.Log


object L {

    @JvmStatic var enabled = true

    private const val CLASS_DELIMITER = "_"

    private fun getCallerCallerClassName(): String? {
        val stackTraceElements = Thread.currentThread().stackTrace
        (1..stackTraceElements.size)
                .asSequence()
                .map { stackTraceElements[it] }
                .filter { it.className.indexOf(Thread::class.java.canonicalName) != 0 }
                .filter { it.className != L.javaClass.canonicalName }
                .forEach {
                    return it.className
                            .split(Regex("\\."))
                            .last()
                            .split(Regex("(?=[A-Z])"))
                            .joinToString(CLASS_DELIMITER)
                            .removePrefix(CLASS_DELIMITER)
                            .toUpperCase()
                }
        return null
    }

    fun d(any: Any?) {
        d(getCallerCallerClassName(), null, any)
    }

    fun d(label: String, any: Any?) {
        d(getCallerCallerClassName(), label, any)
    }

    fun d(tag: String?, label: String?, any: Any?) {
        if (enabled)
            Log.d(tag, "${LogPrefix.APP}${if (label != null) label + " " else ""}${java.lang.String.valueOf(any)}")
    }

    fun e(any: Any?) {
        e(getCallerCallerClassName(), null, any)
    }

    fun e(label: String, any: Any?) {
        e(getCallerCallerClassName(), label, any)
    }

    fun e(tag: String?, label: String?, any: Any?) {
        if (enabled)
            Log.e(tag, "${LogPrefix.APP}${if (label != null) label + " " else ""}${java.lang.String.valueOf(any)}")
    }

    fun i(any: Any?) {
        i(getCallerCallerClassName(), null, any)
    }

    fun i(label: String, any: Any?) {
        i(getCallerCallerClassName(), label, any)
    }

    fun i(tag: String?, label: String?, any: Any?) {
        if (enabled)
            Log.i(tag, "${LogPrefix.APP}${if (label != null) label + " " else ""}${java.lang.String.valueOf(any)}")
    }

}