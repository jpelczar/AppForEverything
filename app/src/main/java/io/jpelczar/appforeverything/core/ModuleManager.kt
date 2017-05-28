package io.jpelczar.appforeverything.core

import io.jpelczar.appforeverything.module.datacollection.DataCollectionModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleManager @Inject constructor() {

    @Inject
    lateinit var dataCollectionModule: DataCollectionModule

}