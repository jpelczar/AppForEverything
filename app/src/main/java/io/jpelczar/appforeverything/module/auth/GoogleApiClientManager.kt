package io.jpelczar.appforeverything.module.auth

import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import io.jpelczar.appforeverything.R

object GoogleApiClientManager {
    var googleApiClient: GoogleApiClient? = null

    fun get(activity: AppCompatActivity, listener: GoogleApiClient.OnConnectionFailedListener): GoogleApiClient {
        if (googleApiClient == null) {

            val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.applicationContext.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            googleApiClient = GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, listener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build()
        }

        return googleApiClient!!
    }
}
