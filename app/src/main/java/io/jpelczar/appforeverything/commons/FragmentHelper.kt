package io.jpelczar.appforeverything.commons

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.core.BaseFragment
import java.lang.reflect.InvocationTargetException


object FragmentHelper {

    fun replaceFragment(a: Activity, mf: Fragment, fr: Fragment, frame: Int, args: Bundle? = null, shouldInject: Boolean = true) {
        if (!a.isFinishing && a is AppCompatActivity) {

            if (shouldInject && a is BaseActivity && fr is BaseFragment) {
                inject(a, fr)
            }

            val fm = mf.childFragmentManager

            if (fm.findFragmentById(frame) != null) {
                if (fr.javaClass.name == fm.findFragmentById(frame).javaClass.name) {
                    return@replaceFragment
                }
            }

            fr.arguments = args ?: Bundle()
            val tr = fm.beginTransaction()
            tr.replace(frame, fr, fr.javaClass.name).commitAllowingStateLoss()
        }
    }

    fun replaceFragment(a: Activity?, fr: Fragment?, frame: Int, argsArg: Bundle? = null, isBackStackAddable: Boolean = false, shouldInject: Boolean = true) {
        var args = argsArg
        if (a != null && !a.isFinishing && a is AppCompatActivity && fr != null) {

            if (shouldInject && a is BaseActivity && fr is BaseFragment) {
                inject(a, fr)
            }

            val fm = a.supportFragmentManager

            if (fm.findFragmentById(frame) != null) {
                if (fr.javaClass.name == fm.findFragmentById(frame).javaClass.name) {
                    return
                }
            }
            if (args == null) {
                args = Bundle()
            }
            fr.arguments = args
            val tr = fm.beginTransaction()

            if (isBackStackAddable) {
                tr.addToBackStack(fr.javaClass.name)
            }
            tr.replace(frame, fr, fr.javaClass.name).commitAllowingStateLoss()
        }
    }

    fun inject(a: Activity, fr: Fragment) {
        try {
            val ac = (a as BaseActivity).activityComponent
            ac.javaClass.getDeclaredMethod("inject", fr.javaClass).invoke(ac, fr)
        } catch (e: IllegalAccessException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity Component " + fr.javaClass.canonicalName)
        } catch (e: InvocationTargetException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity Component " + fr.javaClass.canonicalName)
        } catch (e: NoSuchMethodException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity Component " + fr.javaClass.canonicalName)
        }

    }

    fun popBackStack(a: Activity, fragment: Fragment) {
        val fm = (a as AppCompatActivity).supportFragmentManager
        fm.popBackStackImmediate(fragment.javaClass.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}