package io.jpelczar.appforeverything.module.auth

import android.content.Intent
import android.content.IntentSender
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.data.Account

class GoogleAuth(val activity: AppCompatActivity) : Authentication(activity.applicationContext), GoogleApiClient.OnConnectionFailedListener {

    val REQUEST_SIGN_IN = 324
    val REQUEST_RESOLVE_ERROR = 13214

    var googleApiClient: GoogleApiClient? = null
    var account: Account? = null
    var callback: Callback? = null
    var isSignIn = false
    var resolvingError = false

    init {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }

    override fun signIn(account: Account, callback: Callback) {
        this.account = account
        this.callback = callback

        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        activity.startActivityForResult(signInIntent, REQUEST_SIGN_IN)
    }

    override fun signUp(account: Account, callback: Callback) {
        this.account = account
        this.callback = callback
    }

    override fun createAccount(account: Account, firebaseAuth: FirebaseAuth, callback: Callback) {
        //Empty
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (resolvingError)
            return
        else if (connectionResult.hasResolution()) {
            try {
                resolvingError = true
                connectionResult.startResolutionForResult(activity, REQUEST_RESOLVE_ERROR)
            } catch (e: IntentSender.SendIntentException) {
                googleApiClient?.connect()
            }
        } else {
            callback?.onResult(FAIL, connectionResult.errorMessage)
        }
    }

    override fun signOut(callback: Callback) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { super.signOut(callback) }
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                signIn(account!!, GoogleAuthProvider.getCredential(result.signInAccount!!.idToken, null),
                        callback!!)
            } else {
                callback?.onResult(FAIL, " $requestCode $resultCode ${result.status.statusMessage}")
            }
        } else if (requestCode == REQUEST_RESOLVE_ERROR) {
            L.d("RESOLVE ERROR")
        } else {
            callback?.onResult(FAIL, "Unknown request code")
        }
    }

}
