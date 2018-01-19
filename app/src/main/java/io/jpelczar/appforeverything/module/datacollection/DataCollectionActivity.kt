package io.jpelczar.appforeverything.module.datacollection

import android.os.Bundle
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.FragmentHelper
import io.jpelczar.appforeverything.core.BaseDrawerActivity
import io.jpelczar.appforeverything.core.ModuleManager
import io.jpelczar.appforeverything.module.datacollection.cell.CellDataCollectionFragment
import javax.inject.Inject

class DataCollectionActivity : BaseDrawerActivity() {

    @Inject
    lateinit var moduleManager: ModuleManager

    override fun getLayoutId(): Int = R.layout.activity_data_collection

    override fun inject() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moduleManager.init()

        FragmentHelper.replaceFragment(this, CellDataCollectionFragment(), R.id.fragment_container)
    }
}
