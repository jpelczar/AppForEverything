package io.jpelczar.appforeverything.module.auth

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.GoogleAuthProvider
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.data.Account

class GoogleAuth(val activity: AppCompatActivity) :
        Authentication(activity.applicationContext), GoogleApiClient.OnConnectionFailedListener {

    val REQUEST_SIGN_IN = 324
    val REQUEST_RESOLVE_ERROR = 13214

    var googleApiClient: GoogleApiClient? = null
    var callback: Callback? = null
    var resolvingError = false

    init {
        googleApiClient = GoogleApiClientManager.get(activity, this)
    }

    override fun signIn(callback: Callback) {
        this.callback = callback

        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        activity.startActivityForResult(signInIntent, REQUEST_SIGN_IN)
    }

    override fun signOut(callback: Callback) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { super.signOut(callback) }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess && result.signInAccount?.idToken != null) {
                signIn(GoogleAuthProvider.getCredential(result.signInAccount!!.idToken, null),
                        object : Callback {
                            override fun onResult(state: Long, message: String?, account: Account?) {
                                if (state == SIGN_IN_SUCCESS) {
                                    account?.photoUrl = result.signInAccount?.photoUrl ?:
                                            Uri.parse(context.getString(R.string.placeholder_profile_image))
                                }
                                callback?.onResult(state, message, account)
                            }
                        })
            } else {
                callback?.onResult(SIGN_IN_FAIL,
                        "requestCode: $requestCode resultCode: $resultCode status: ${result.status.statusMessage}")
            }
        } else if (requestCode == REQUEST_RESOLVE_ERROR) {
            L.d("RESOLVE ERROR")
        } else {
            callback?.onResult(FAIL, context.getString(R.string.unknown_request_code))
        }
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
}
