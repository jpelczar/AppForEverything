package io.jpelczar.appforeverything.core

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.data.Account
import javax.inject.Inject


open class BaseFragment : Fragment() {
    @Inject
    lateinit var activity: AppCompatActivity

    @Inject
    lateinit var currentAccount: Account
}
