package io.jpelczar.appforeverything.core.injection.activity

import dagger.Component
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent
import io.jpelczar.appforeverything.signin.SignInActivity
import io.jpelczar.appforeverything.signin.SignInFragment

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(signInActivity: SignInActivity)

    fun inject(signInFragment: SignInFragment)

    //use this if adding new Fragment or Activity
    //void inject(SomeActivity someActivity);
    //void inject(SomeFragment someFragment);
}
