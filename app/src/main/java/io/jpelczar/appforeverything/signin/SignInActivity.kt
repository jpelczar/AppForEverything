package io.jpelczar.appforeverything.signin

import android.content.Intent
import android.os.Bundle
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.core.commons.FragmentHelper
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    private val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        activityComponent.inject(this)

        FragmentHelper.replaceFragment(this, signInFragment, R.id.main_frame)

        open.setOnClickListener { FragmentHelper.replaceFragment(this, signInFragment, R.id.main_frame) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInFragment.onActivityResult(requestCode, resultCode, data)
    }
}
