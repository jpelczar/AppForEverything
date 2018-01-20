package io.jpelczar.appforeverything.core

import android.app.Application
import android.content.Context
import android.content.Intent
import com.squareup.leakcanary.LeakCanary
import io.jpelczar.appforeverything.ApplicationService
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent
import io.jpelczar.appforeverything.core.injection.application.ApplicationModule
import io.jpelczar.appforeverything.core.injection.application.DaggerApplicationComponent


class App : Application() {
    companion object {
        @JvmStatic
        lateinit var applicationComponent: ApplicationComponent

        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        startService(Intent(this, ApplicationService::class.java))
    }
}