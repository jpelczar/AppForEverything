package io.jpelczar.appforeverything

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.AccountPersister
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.signin.MainActivity
import io.jpelczar.appforeverything.signin.SignInActivity
import io.jpelczar.core.commons.L
import io.jpelczar.core.commons.LogPrefix.AUTH

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)

        if (Authentication.isAuthenticated()) {
            currentAccount.fill(Authentication.getCurrentUser()
                    ?: AccountPersister.load(this.applicationContext))
            L.d(AUTH, "Current account - $currentAccount")
            if (AccountPersister.isAccountExpired(applicationContext)) {
                L.d(AUTH, "Auth expired")
                Authentication.getAuthenticatorForAccount(this, currentAccount)?.signOut(object : Authentication.Callback {
                    @SuppressLint("SwitchIntDef")
                    override fun onResult(state: Int, message: String?, account: Account?) {
                        when (state) {
                            Authentication.SIGN_OUT_SUCCESS -> startActivity(Intent(this@StartActivity, SignInActivity::class.java))
                            else -> {
                                Toast.makeText(this@StartActivity, R.string.user_not_sign_out, Toast.LENGTH_SHORT).show()
                                L.d(AUTH, "Sin out fail. State $state")
                            }
                        }
                    }
                })
            } else {
                AccountPersister.refreshValidationPeriod(applicationContext)
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            L.d(AUTH, "No active auth")
            startActivity(Intent(this, SignInActivity::class.java))
        }
        finish()
    }
}
