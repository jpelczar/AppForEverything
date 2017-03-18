package io.jpelczar.appforeverything.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.core.injection.activity.ActivityComponent
import io.jpelczar.appforeverything.core.injection.activity.ActivityModule
import io.jpelczar.appforeverything.core.injection.activity.DaggerActivityComponent
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.auth.Authentication.Callback


open class BaseActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
    lateinit var authentication: Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .applicationComponent(App.applicationComponent).build()
    }

    override fun onStart() {
        super.onStart()
        authentication.registerAuthListener(object : Callback {
            override fun onResult(state: Long, message: String?) {
                L.d("$state - $message")
            }
        })
    }

    override fun onStop() {
        super.onStop()
        authentication.unregisterAuthListener()
    }
}
