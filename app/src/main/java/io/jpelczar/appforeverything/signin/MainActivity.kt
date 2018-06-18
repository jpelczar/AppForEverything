package io.jpelczar.appforeverything.signin

import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.module.ModuleBaseActivity

class MainActivity : ModuleBaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun inject() = activityComponent.inject(this)
}
