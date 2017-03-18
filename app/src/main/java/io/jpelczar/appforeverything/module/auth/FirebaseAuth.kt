package io.jpelczar.appforeverything.module.auth

import android.content.Context
import io.jpelczar.appforeverything.data.Account

class FirebaseAuth(context: Context) : Authentication(context) {

    override fun sigIn(account: Account, password: String) {
    }

    override fun sigUp(account: Account, password: String, callback: Callback) {
        firebaseAuth.createUserWithEmailAndPassword(account.mail, password)
                .addOnSuccessListener { result -> callback.onResult(SIGN_UP_SUCCESS, result.user.email) }
                .addOnFailureListener { e -> callback.onResult(SIGN_UP_FAIL, e.message) }
    }

    override fun signOut(account: Account) {
    }
}
