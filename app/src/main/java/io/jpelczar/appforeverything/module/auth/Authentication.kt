package io.jpelczar.appforeverything.module.auth

import android.content.Context
import android.support.annotation.IntDef
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account


abstract class Authentication(val context: Context) {

    protected var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener


    abstract fun sigIn(account: Account, password: String)

    abstract fun sigUp(account: Account, password: String, callback: Callback)

    abstract fun signOut(account: Account)

    fun registerAuthListener(callback: Callback) {
        firebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                callback.onResult(SIGN_IN_SUCCESS)
            } else {
                callback.onResult(SIGN_UP_SUCCESS)
            }
        }
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    fun unregisterAuthListener() {
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    interface Callback {
        fun onResult(@State state: Long, message: String? = null)
    }

    companion object {
        const val SIGN_IN_SUCCESS = 0L
        const val SIGN_IN_FAIL = 1L
        const val SIGN_OUT_SUCCESS = 2L
        const val SIGN_OUT_FAIL = 3L
        const val SIGN_UP_SUCCESS = 4L
        const val SIGN_UP_FAIL = 5L
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(SIGN_IN_FAIL, SIGN_IN_SUCCESS, SIGN_OUT_FAIL, SIGN_OUT_SUCCESS, SIGN_UP_FAIL, SIGN_UP_SUCCESS)
    annotation class State
}