package io.jpelczar.appforeverything.core

import android.os.Bundle
import io.jpelczar.appforeverything.core.injection.activity.ActivityModule
import io.jpelczar.appforeverything.core.injection.activity.DaggerActivityComponent
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.core.injection.InjectableActivity
import javax.inject.Inject


abstract class BaseActivity : InjectableActivity() {

    @Inject
    lateinit var currentAccount: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .applicationComponent(App.applicationComponent).build()
    }
}
