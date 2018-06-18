package io.jpelczar.appforeverything.core.injection.application

import android.app.Application
import android.content.Context
import dagger.Component
import io.jpelczar.appforeverything.core.ModuleManager
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.core.event.RxBus
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun rxBus(): RxBus

    fun account(): Account

    fun moduleManager(): ModuleManager

    fun inject(moduleManager: ModuleManager)

}