package io.jpelczar.appforeverything.core.injection.service

import dagger.Component
import io.jpelczar.appforeverything.ApplicationService
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent

@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ServiceModule::class))
interface SeviceComponent {

    fun inject(applicationService: ApplicationService)

}