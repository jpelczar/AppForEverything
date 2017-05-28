package io.jpelczar.appforeverything.core

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class ApplicationService : Service() {

    override fun onBind(intent: Intent?): IBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    inner class LocalBinder: Binder() {
        fun getService() : ApplicationService = this@ApplicationService
    }
}
