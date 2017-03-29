package io.jpelczar.appforeverything.module.auth

import android.content.Context
import android.support.annotation.IntDef
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account


abstract class Authentication(val context: Context) {

    protected var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener

    abstract fun signIn(account: Account, callback: Callback)

    abstract fun signUp(account: Account, callback: Callback)

    abstract fun createAccount(account: Account, firebaseAuth: FirebaseAuth, callback: Callback)

    fun signOut(callback: Callback) {
        registerAuthListener(callback)
        firebaseAuth.signOut()
        unregisterAuthListener()
    }

    protected fun signIn(account: Account, credential: AuthCredential, callback: Callback) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful)
                callback.onResult(SIGN_IN_SUCCESS, task.result.user.email)
            else
                callback.onResult(SIGN_IN_FAIL, task.exception?.message)
        }
    }

    protected fun signUp(account: Account, credential: AuthCredential, callback: Callback) {
        if (firebaseAuth.currentUser != null)
            firebaseAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onResult(SIGN_UP_SUCCESS, credential.provider)
                else
                    callback.onResult(SIGN_UP_FAIL, credential.provider + " " + task.exception)
            }
        else
            createAccount(account, firebaseAuth, callback)
    }

    private fun registerAuthListener(callback: Callback) {
        firebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null)
                callback.onResult(SIGN_OUT_SUCCESS)
            else
                callback.onResult(SIGN_OUT_FAIL)
        }
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    private fun unregisterAuthListener() {
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

        fun translateAuthState(value: Long): String {
            return when (value) {
                SIGN_IN_SUCCESS -> "Sign in success"
                SIGN_IN_FAIL -> "Sign in fail"
                SIGN_OUT_SUCCESS -> "sign out success"
                SIGN_OUT_FAIL -> "Sign out fail"
                SIGN_UP_SUCCESS -> "Sign up success"
                SIGN_UP_FAIL -> "Sign up fail"
                else -> "Not auth state"
            }
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(SIGN_IN_FAIL, SIGN_IN_SUCCESS, SIGN_OUT_FAIL, SIGN_OUT_SUCCESS, SIGN_UP_FAIL, SIGN_UP_SUCCESS)
    annotation class State
}