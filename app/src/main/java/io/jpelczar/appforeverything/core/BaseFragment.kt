package io.jpelczar.appforeverything.core

import android.app.Activity
import android.support.v4.app.Fragment
import javax.inject.Inject


open class BaseFragment : Fragment() {
    @Inject
    lateinit var activity: Activity
}
