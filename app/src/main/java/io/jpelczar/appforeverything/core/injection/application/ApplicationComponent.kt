package io.jpelczar.appforeverything.core.injection.application

import android.app.Application
import android.content.Context
import dagger.Component
import io.jpelczar.appforeverything.core.event.RxBus
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun rxBus(): RxBus

}