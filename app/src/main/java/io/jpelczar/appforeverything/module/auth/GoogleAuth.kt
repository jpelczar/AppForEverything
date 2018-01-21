package io.jpelczar.appforeverything.module.auth

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.data.Account


class GoogleAuth(val activity: AppCompatActivity) :
        Authentication(activity.applicationContext) {

    companion object {
        private const val REQUEST_SIGN_IN = 324
    }

    var googleSignInClient: GoogleSignInClient? = null
    var callback: Callback? = null

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.google_default_web_client_id))
                .requestProfile()
                .requestEmail()
                .requestId()
                .build()
        googleSignInClient = GoogleSignIn.getClient(activity.applicationContext, gso)
    }

    override fun signIn(callback: Callback) {
        this.callback = callback

        val signInIntent: Intent = googleSignInClient?.signInIntent ?: Intent()
        activity.startActivityForResult(signInIntent, REQUEST_SIGN_IN)
    }

    override fun signOut(callback: Callback) {
        googleSignInClient?.signOut()?.addOnCompleteListener { task ->
            when (task.isSuccessful) {
                true -> super.signOut(callback)
                false -> callback.onResult(Authentication.SIGN_OUT_FAIL)
            }
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_SIGN_IN -> {
                val signInTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val signInAccount = signInTask.getResult(Exception::class.java)
                    signIn(GoogleAuthProvider.getCredential(signInAccount.idToken, null),
                            object : Callback {
                                override fun onResult(state: Long, message: String?, account: Account?) {
                                    if (state == SIGN_IN_SUCCESS) {
                                        account?.photoUrl = signInAccount.photoUrl ?: Uri.parse(context.getString(R.string.placeholder_profile_image_uri))
                                    }
                                    callback?.onResult(state, message, account)
                                }
                            })
                } catch (e: Exception) {
                    callback?.onResult(SIGN_IN_FAIL,
                            "requestCode: $requestCode resultCode: $resultCode status: ${e.message}")
                }
            }
            else -> callback?.onResult(FAIL, context.getString(R.string.unknown_request_code))
        }
    }
}
