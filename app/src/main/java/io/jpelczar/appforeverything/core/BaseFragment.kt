package io.jpelczar.appforeverything.core

import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.core.injection.InjectableFragment
import javax.inject.Inject


open class BaseFragment : InjectableFragment() {
    @Inject
    lateinit var activity: AppCompatActivity

    @Inject
    lateinit var currentAccount: Account
}
