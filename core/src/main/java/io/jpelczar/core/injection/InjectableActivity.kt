package io.jpelczar.core.injection

import android.support.v7.app.AppCompatActivity

abstract class InjectableActivity : AppCompatActivity() {
    lateinit var activityComponent: CoreActivityComponent
}
