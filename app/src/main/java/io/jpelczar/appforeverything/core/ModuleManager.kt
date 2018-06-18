package io.jpelczar.appforeverything.core

import android.content.Context

class ModuleManager(val context: Context) {

    fun init() {
        App.applicationComponent.inject(this)

    }
}