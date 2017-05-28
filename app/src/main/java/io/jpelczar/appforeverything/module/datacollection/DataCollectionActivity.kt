package io.jpelczar.appforeverything.module.datacollection

import android.os.Bundle
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.core.ModuleManager
import javax.inject.Inject

class DataCollectionActivity : BaseActivity() {

    @Inject
    lateinit var moduleManager: ModuleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_collection)
        activityComponent.inject(this)
        moduleManager.init()

        L.d(moduleManager.dataCollectionModule)
    }
}
