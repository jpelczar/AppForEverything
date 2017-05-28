package io.jpelczar.appforeverything.module.datacollection.injection

import dagger.Component
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent
import io.jpelczar.appforeverything.module.datacollection.DataCollectionModule

@PerModule
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(DataCollectionInjectionModule::class))
interface DataCollectionInjectionComponent {

    fun inject(dataCollectionModule: DataCollectionModule)

}