package io.jpelczar.appforeverything.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.core.injection.activity.ActivityComponent
import io.jpelczar.appforeverything.core.injection.activity.ActivityModule
import io.jpelczar.appforeverything.core.injection.activity.DaggerActivityComponent
import io.jpelczar.appforeverything.data.Account
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var currentAccount: Account

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .applicationComponent(App.applicationComponent).build()
    }
}
