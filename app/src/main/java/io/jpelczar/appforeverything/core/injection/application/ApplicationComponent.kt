package io.jpelczar.appforeverything.core.injection.application

import android.app.Application
import android.content.Context
import dagger.Component
import io.jpelczar.appforeverything.core.ModuleManager
import io.jpelczar.appforeverything.core.event.RxBus
import io.jpelczar.appforeverything.data.Account
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun rxBus(): RxBus

    fun account(): Account

    fun moduleManager(): ModuleManager

}