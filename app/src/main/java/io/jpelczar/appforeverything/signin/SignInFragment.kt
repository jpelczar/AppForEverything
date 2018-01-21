package io.jpelczar.appforeverything.signin


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.commons.LogPrefix.AUTH
import io.jpelczar.appforeverything.core.BaseFragment
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.auth.FacebookAuth
import io.jpelczar.appforeverything.module.auth.FirebaseAuth
import io.jpelczar.appforeverything.module.auth.GoogleAuth
import io.jpelczar.appforeverything.module.datacollection.DataCollectionActivity

class SignInFragment : BaseFragment(), Authentication.Callback {

    private var fragmentView: View? = null

    @BindView(R.id.sign_up_button)
    lateinit var signUpButton: Button

    @BindView(R.id.sign_in_button)
    lateinit var signInButton: Button

    @BindView(R.id.sign_in_facebook_button)
    lateinit var signInFacebook: Button

    @BindView(R.id.sign_in_google_button)
    lateinit var signInGoogle: Button

    @BindView(R.id.mail_edit_text)
    lateinit var mailEditText: EditText

    @BindView(R.id.password_edit_text)
    lateinit var passwordEditText: EditText

    private var progressDialog: ProgressDialog? = null

    private var authentication: Authentication? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        ButterKnife.bind(this, fragmentView!!)

        setUpListeners()

        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authentication?.handleResult(requestCode, resultCode, data)
    }

    override fun onResult(state: Long, message: String?, account: Account?) {
        if (account != null) {
            currentAccount.fill(account)
        }

        progressDialog?.dismiss()
        L.d(AUTH, "${Authentication.translateAuthState(state)} - $message")

        if (state == Authentication.SIGN_IN_SUCCESS || state == Authentication.SIGN_UP_SUCCESS) {
            startActivity(Intent(context, DataCollectionActivity::class.java))
            activity.finish()
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListeners() {
        signUpButton.setOnClickListener {
            if (checkUserDataAndShowFail(mailEditText.text.toString(), passwordEditText.text.toString())) {
                authentication = FirebaseAuth(activity.applicationContext,
                        mailEditText.text.toString(), passwordEditText.text.toString())
                authentication?.signUp(this)
            }
        }

        signInButton.setOnClickListener {
            if (checkUserDataAndShowFail(mailEditText.text.toString(), passwordEditText.text.toString())) {
                authentication = FirebaseAuth(activity.applicationContext,
                        mailEditText.text.toString(), passwordEditText.text.toString())
                baseSignInListener()
            }
        }

        signInFacebook.setOnClickListener {
            authentication = FacebookAuth(activity)
            baseSignInListener()
        }

        signInGoogle.setOnClickListener {
            authentication = GoogleAuth(activity)
            baseSignInListener()
        }
    }

    private fun baseSignInListener() {
        progressDialog = ProgressDialog.show(activity, "Loading", "Wait while user is authenticated...")
        authentication?.signIn(this)
    }

    private fun checkUserDataAndShowFail(mail: String, password: String): Boolean {
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
            AlertDialog.Builder(activity).setMessage(R.string.empty_user_data).create().show()
            return false
        }
        return true
    }

}
