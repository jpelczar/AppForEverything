package io.jpelczar.appforeverything

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.jpelczar.appforeverything.core.BaseService

class ApplicationService : BaseService() {

    override fun onBind(intent: Intent?): IBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        serviceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        fun getService(): ApplicationService = this@ApplicationService
    }
}
