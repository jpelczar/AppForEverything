package io.jpelczar.appforeverything.core.injection.activity

import dagger.Component
import io.jpelczar.appforeverything.StartActivity
import io.jpelczar.appforeverything.core.injection.application.ApplicationComponent
import io.jpelczar.appforeverything.signin.SignInActivity
import io.jpelczar.appforeverything.signin.SignInFragment
import io.jpelczar.core.injection.CoreActivityComponent

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent : CoreActivityComponent {

    fun inject(startActivity: StartActivity)

    fun inject(signInActivity: SignInActivity)

    fun inject(signInFragment: SignInFragment)
}
