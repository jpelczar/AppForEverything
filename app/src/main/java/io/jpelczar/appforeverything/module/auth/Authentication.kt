package io.jpelczar.appforeverything.module.auth

import android.content.Context
import android.content.Intent
import android.support.annotation.IntDef
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.core.commons.L
import io.jpelczar.core.commons.LogPrefix

abstract class Authentication(val context: Context) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener

    abstract fun signIn(callback: Callback)

    open fun signUp(callback: Callback) {}

    open fun createAccount(firebaseAuth: FirebaseAuth, callback: Callback) {}

    open fun signOut(callback: Callback) {
        registerAuthListener(callback)
        firebaseAuth.signOut()
        unregisterAuthListener()
    }

    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    protected fun signIn(credential: AuthCredential, callback: Callback) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                AccountPersister.refreshValidationPeriod(context)
                val account = Account().setFromFirebase(task.result.user)
                AccountPersister.persist(context, account)
                callback.onResult(SIGN_IN_SUCCESS, null, account)
            } else
                callback.onResult(SIGN_IN_FAIL, task.exception?.message)
        }
    }

    protected fun signUp(credential: AuthCredential, callback: Callback) {
        if (firebaseAuth.currentUser != null)
            firebaseAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onResult(SIGN_UP_SUCCESS, credential.provider)
                else
                    callback.onResult(SIGN_UP_FAIL, credential.provider + " " + task.exception)
            }
        else
            createAccount(firebaseAuth, callback)
    }

    private fun registerAuthListener(callback: Callback) {
        firebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                AccountPersister.clean(context)
                callback.onResult(SIGN_OUT_SUCCESS)
            } else
                callback.onResult(SIGN_OUT_FAIL)
            L.d(LogPrefix.AUTH, "User sign out: ${!isAuthenticated()}")
        }
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    private fun unregisterAuthListener() {
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    interface Callback {
        fun onResult(@State state: Int, message: String? = null, account: Account? = null)
    }

    companion object {
        const val SIGN_IN_SUCCESS = 0
        const val SIGN_IN_FAIL = 1
        const val SIGN_OUT_SUCCESS = 2
        const val SIGN_OUT_FAIL = 3
        const val SIGN_UP_SUCCESS = 4
        const val SIGN_UP_FAIL = 5
        const val FAIL = 6

        fun translateAuthState(value: Int): String {
            return when (value) {
                SIGN_IN_SUCCESS -> "Sign in success"
                SIGN_IN_FAIL -> "Sign in fail"
                SIGN_OUT_SUCCESS -> "sign out success"
                SIGN_OUT_FAIL -> "Sign out fail"
                SIGN_UP_SUCCESS -> "Sign up success"
                SIGN_UP_FAIL -> "Sign up fail"
                FAIL -> "Something went wrong"
                else -> "Not auth state"
            }
        }

        fun isAuthenticated(): Boolean = FirebaseAuth.getInstance().currentUser != null


        fun getCurrentUser(): Account? {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return when (currentUser != null) {
                true -> Account().setFromFirebase(currentUser!!)
                else -> null
            }
        }

        fun getAuthenticatorForAccount(activity: AppCompatActivity, account: Account?): Authentication? {
            if (account == null)
                return null

            val providers = account.providers
            var authenticator: Authentication = io.jpelczar.appforeverything.module.auth
                    .FirebaseAuth(activity.applicationContext)
            if (providers.any { item -> item == Account.GOOGLE }) {
                authenticator = GoogleAuth(activity)
            }

            return authenticator
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(SIGN_IN_FAIL, SIGN_IN_SUCCESS, SIGN_OUT_FAIL, SIGN_OUT_SUCCESS, SIGN_UP_FAIL, SIGN_UP_SUCCESS, FAIL)
    annotation class State
}