package io.jpelczar.appforeverything

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.datacollection.DataCollectionActivity
import io.jpelczar.appforeverything.signin.SignInActivity

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        activityComponent.inject(this)

        if (Authentication.isAuthenticated()) {
            currentAccount.fill(Authentication.getCurrentUser()!!)
            startActivity(Intent(this, DataCollectionActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        finish()
    }
}
