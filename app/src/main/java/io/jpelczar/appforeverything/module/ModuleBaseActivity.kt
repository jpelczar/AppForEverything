package io.jpelczar.appforeverything.module

import android.view.LayoutInflater
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.core.BaseDrawerActivity

abstract class ModuleBaseActivity : BaseDrawerActivity() {

    override fun initNavigationHeader() {
        LayoutInflater.from(this).inflate(R.layout.header_drawer_module, headerContainer, true)
    }
}
