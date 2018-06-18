package io.jpelczar.core.commons

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import io.jpelczar.core.injection.InjectableActivity
import io.jpelczar.core.injection.InjectableFragment
import java.lang.reflect.InvocationTargetException


object FragmentHelper {

    fun replaceFragment(a: Activity?, fr: Fragment?, frame: Int, argsArg: Bundle? = null, isBackStackAddable: Boolean = false, shouldInject: Boolean = true) {
        var args = argsArg
        if (a != null && !a.isFinishing && a is AppCompatActivity && fr != null) {

            if (shouldInject && a is InjectableActivity && fr is InjectableFragment) {
                inject(a, fr)
            }

            val fm = a.supportFragmentManager

            if (fm.findFragmentById(frame) != null) {
                if (fr.javaClass.name == fm.findFragmentById(frame).javaClass.name) {
                    return
                }
            }

            fr.arguments = args ?: Bundle()
            val tr = fm.beginTransaction()
            L.d("Start TR")


            if (isBackStackAddable) {
                tr.addToBackStack(fr.javaClass.name)
                L.d("Add to BS")
            }
            tr.replace(frame, fr, fr.javaClass.name).commitAllowingStateLoss()
            L.d("Replaced and commited")
        }
    }

    fun inject(a: Activity, fr: Fragment) {
        try {
            val ac = (a as InjectableActivity).activityComponent
            ac.javaClass.getDeclaredMethod("inject", fr.javaClass).invoke(ac, fr)
            L.d("Injected")
        } catch (e: IllegalAccessException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity CoreActivityComponent " + fr.javaClass.canonicalName)
        } catch (e: InvocationTargetException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity CoreActivityComponent " + fr.javaClass.canonicalName)
        } catch (e: NoSuchMethodException) {
            L.e("Exception in Fragment Helper. Issue with inject method from Activity CoreActivityComponent " + fr.javaClass.canonicalName)
        }

    }

    fun popBackStack(a: Activity, fragment: Fragment) {
        val fm = (a as AppCompatActivity).supportFragmentManager
        fm.popBackStackImmediate(fragment.javaClass.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}