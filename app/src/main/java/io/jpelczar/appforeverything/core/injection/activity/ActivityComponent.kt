package io.jpelczar.appforeverything.core.injection.activity

import dagger.Component
import io.jpelczar.appforeverything.MainActivity
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    //use this if adding new Fragment or Activity
    //void inject(SomeActivity someActivity);
    //void inject(SomeFragment someFragment);
}
