package io.jpelczar.appforeverything.core.injection.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }


}
