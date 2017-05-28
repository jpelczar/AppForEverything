package io.jpelczar.appforeverything.signin

import android.content.Intent
import android.os.Bundle
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.FragmentHelper
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.datacollection.DataCollectionActivity

class SignInActivity : BaseActivity() {

    val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        activityComponent.inject(this)

        if (Authentication.isAuthenticated()) {
            currentAccount.fill(Authentication.getCurrentUser()!!)
            startActivity(Intent(this, DataCollectionActivity::class.java))
        }

        FragmentHelper.replaceFragment(this, signInFragment, R.id.main_frame)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInFragment.onActivityResult(requestCode, resultCode, data)
    }
}
