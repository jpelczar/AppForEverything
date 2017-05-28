package io.jpelczar.appforeverything.core

import android.app.Service
import io.jpelczar.appforeverything.core.injection.service.DaggerSeviceComponent
import io.jpelczar.appforeverything.core.injection.service.ServiceModule
import io.jpelczar.appforeverything.core.injection.service.SeviceComponent

abstract class BaseService : Service() {

    lateinit var serviceComponent: SeviceComponent

    override fun onCreate() {
        super.onCreate()
        serviceComponent = DaggerSeviceComponent.builder().serviceModule(ServiceModule())
                .applicationComponent(App.applicationComponent).build()
    }
}