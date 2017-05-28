package io.jpelczar.appforeverything.signin

import android.content.Intent
import android.os.Bundle
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.FragmentHelper
import io.jpelczar.appforeverything.core.BaseActivity

class SignInActivity : BaseActivity() {

    val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        activityComponent.inject(this)

        FragmentHelper.replaceFragment(this, signInFragment, R.id.main_frame)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInFragment.onActivityResult(requestCode, resultCode, data)
    }
}
