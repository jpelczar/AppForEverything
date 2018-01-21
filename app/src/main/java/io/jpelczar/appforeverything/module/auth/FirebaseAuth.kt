package io.jpelczar.appforeverything.module.auth

import android.content.Context
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.jpelczar.appforeverything.data.Account

class FirebaseAuth(context: Context, private val mail: String = "", private val password: String= "") : Authentication(context) {

    override fun signUp(callback: Callback) {
        signUp(EmailAuthProvider.getCredential(mail, password), callback)
    }

    override fun signIn(callback: Callback) {
        signIn(EmailAuthProvider.getCredential(mail, password), callback)
    }

    override fun createAccount(firebaseAuth: FirebaseAuth, callback: Callback) {
        firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnSuccessListener { task ->
                    callback.onResult(SIGN_UP_SUCCESS, "Create account success " + task.user.displayName)
                }
                .addOnFailureListener { task ->
                    callback.onResult(SIGN_UP_FAIL, "Can't create account " + task.message)
                }
    }
}
