package io.jpelczar.appforeverything.core

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject


open class BaseFragment : Fragment() {
    @Inject
    lateinit var activity: AppCompatActivity
}
