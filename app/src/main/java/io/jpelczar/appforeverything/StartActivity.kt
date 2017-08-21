package io.jpelczar.appforeverything

import android.content.Intent
import android.os.Bundle
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.commons.LogPrefix.AUTH
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.datacollection.DataCollectionActivity
import io.jpelczar.appforeverything.signin.SignInActivity

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)

        if (Authentication.isAuthenticated()) {
            currentAccount.fill(Authentication.getCurrentUser()!!)
            L.d(AUTH, "Current account - $currentAccount")
            if (Authentication.isExpired()) {
                L.d(AUTH, "Auth expired")
                Authentication.getAuthenticatorForAccount(this, currentAccount)
                startActivity(Intent(this, SignInActivity::class.java))
            } else {
                startActivity(Intent(this, DataCollectionActivity::class.java))
            }
        } else {
            L.d(AUTH, "No active auth")
            startActivity(Intent(this, SignInActivity::class.java))
        }
        finish()
    }
}
