package io.jpelczar.appforeverything.module.auth

import android.content.Context
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account

class FirebaseAuth(context: Context, account: Account) : Authentication(context, account) {

    override fun signUp(callback: Callback) {
        signUp(EmailAuthProvider.getCredential(account.mail, account.password), callback)
    }

    override fun signIn(callback: Callback) {
        signIn(EmailAuthProvider.getCredential(account.mail, account.password), callback)
    }

    override fun createAccount(firebaseAuth: FirebaseAuth, callback: Callback) {
        firebaseAuth.createUserWithEmailAndPassword(account.mail, account.password)
                .addOnSuccessListener { task ->
                    callback.onResult(SIGN_UP_SUCCESS, "Create account success " + task.user.displayName)
                }
                .addOnFailureListener { task ->
                    callback.onResult(SIGN_UP_FAIL, "Can't create account " + task.message)
                }
    }
}
