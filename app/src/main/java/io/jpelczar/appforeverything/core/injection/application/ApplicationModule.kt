package io.jpelczar.appforeverything.core.injection.application

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.jpelczar.appforeverything.core.event.RxBus
import io.jpelczar.appforeverything.data.Account
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideRxBus(): RxBus {
        return RxBus()
    }

    @Provides
    @Singleton
    internal fun provideAccount(): Account {
        return Account()
    }

}
