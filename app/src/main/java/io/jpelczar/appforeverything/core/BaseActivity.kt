package io.jpelczar.appforeverything.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.core.injection.activity.ActivityComponent
import io.jpelczar.appforeverything.core.injection.activity.ActivityModule
import io.jpelczar.appforeverything.core.injection.activity.DaggerActivityComponent
import io.jpelczar.appforeverything.module.auth.Authentication


open class BaseActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
    lateinit var authentication: Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .applicationComponent(App.applicationComponent).build()
    }
}
