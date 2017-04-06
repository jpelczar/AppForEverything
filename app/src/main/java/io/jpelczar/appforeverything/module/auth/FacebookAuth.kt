package io.jpelczar.appforeverything.module.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import io.jpelczar.appforeverything.data.Account

class FacebookAuth(val activity: AppCompatActivity, account: Account) : Authentication(activity.applicationContext, account) {

    val loginManager: LoginManager = LoginManager.getInstance()
    val callbackManager: CallbackManager = CallbackManager.Factory.create()
    var accessToken: AccessToken? = null

    override fun signIn(callback: Callback) {

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onError(error: FacebookException?) {
                callback.onResult(SIGN_IN_FAIL, error?.message)
            }

            override fun onCancel() {
                callback.onResult(SIGN_IN_FAIL, "canceled")
            }

            override fun onSuccess(result: LoginResult?) {
                accessToken = result?.accessToken
                if (accessToken != null && !TextUtils.isEmpty(accessToken!!.token))
                    signIn(FacebookAuthProvider.getCredential(accessToken!!.token!!), callback)
            }
        })

        loginManager.logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}