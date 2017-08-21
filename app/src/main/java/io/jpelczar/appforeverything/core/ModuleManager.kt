package io.jpelczar.appforeverything.core

import android.content.Context
import io.jpelczar.appforeverything.module.datacollection.DataCollectionModule
import io.jpelczar.appforeverything.module.datacollection.injection.DaggerDataCollectionInjectionComponent
import io.jpelczar.appforeverything.module.datacollection.injection.DataCollectionInjectionModule
import javax.inject.Inject

class ModuleManager(val context: Context) {

    @Inject
    lateinit var dataCollectionModule: DataCollectionModule

    fun init() {
        App.applicationComponent.inject(this)
        DaggerDataCollectionInjectionComponent.builder().dataCollectionInjectionModule(DataCollectionInjectionModule(context))
                .applicationComponent(App.applicationComponent).build().inject(dataCollectionModule)
    }

}