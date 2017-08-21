package io.jpelczar.appforeverything.module.datacollection.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import io.jpelczar.appforeverything.module.datacollection.cell.CellDataProvider


@Module
class DataCollectionInjectionModule(private val context: Context) {

    @Provides
    fun provideCellDataProvider() = CellDataProvider(context)

}