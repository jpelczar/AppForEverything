package io.jpelczar.appforeverything.module.auth

import android.content.Context
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account

class FirebaseAuth(context: Context) : Authentication(context) {

    override fun signUp(account: Account, callback: Callback) {
        signUp(account, EmailAuthProvider.getCredential(account.mail, account.password), callback)
    }

    override fun signIn(account: Account, callback: Callback) {
        signIn(account, EmailAuthProvider.getCredential(account.mail, account.password), callback)
    }

    override fun createAccount(account: Account, firebaseAuth: FirebaseAuth, callback: Callback) {
        firebaseAuth.createUserWithEmailAndPassword(account.mail, account.password)
                .addOnSuccessListener { task ->
                    callback.onResult(SIGN_UP_SUCCESS, "Create account success " + task.user.displayName)
                }
                .addOnFailureListener { task ->
                    callback.onResult(SIGN_UP_FAIL, "Can't create account " + task.message)
                }
    }
}
