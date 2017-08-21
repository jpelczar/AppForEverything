package io.jpelczar.appforeverything.module.datacollection

import android.content.Context
import io.jpelczar.appforeverything.module.Module
import io.jpelczar.appforeverything.module.datacollection.cell.CellDataProvider
import javax.inject.Inject

class DataCollectionModule(val context: Context) : Module {

    @Inject
    lateinit var cellDataProvider: CellDataProvider

}