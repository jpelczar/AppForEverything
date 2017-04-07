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
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.core.BaseFragment
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.auth.FacebookAuth
import io.jpelczar.appforeverything.module.auth.FirebaseAuth
import io.jpelczar.appforeverything.module.auth.GoogleAuth

class SignInFragment : BaseFragment(), Authentication.Callback {

    var fragmentView: View? = null

    @BindView(R.id.sign_up_button)
    lateinit var signUpButton: Button

    @BindView(R.id.sign_in_button)
    lateinit var signInButton: Button

    @BindView(R.id.sign_in_facebook_button)
    lateinit var signInFacebook: Button

    @BindView(R.id.sign_in_google_button)
    lateinit var signInGoogle: Button

    @BindView(R.id.sign_out_button)
    lateinit var signOutButton: Button

    @BindView(R.id.mail_edit_text)
    lateinit var mailEditText: EditText

    @BindView(R.id.password_edit_text)
    lateinit var passworkdEditText: EditText

    @BindView(R.id.auth_result_text_view)
    lateinit var authOutTextView: TextView

    var progressDialog: ProgressDialog? = null

    var authentication: Authentication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater!!.inflate(R.layout.fragment_sign_in, container, false)
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

        val displayText = "${Authentication.translateAuthState(state)} - $message - $currentAccount"
        progressDialog?.dismiss()
        authOutTextView.text = displayText
        L.d(displayText)
    }

    fun setUpListeners() {
        signUpButton.setOnClickListener {
            if (checkUserData(mailEditText.text.toString(), passworkdEditText.text.toString())) {
                authentication = FirebaseAuth(activity.applicationContext,
                        mailEditText.text.toString(), passworkdEditText.text.toString())
                authentication?.signUp(this)
            }
        }

        signInButton.setOnClickListener {
            if (checkUserData(mailEditText.text.toString(), passworkdEditText.text.toString())) {
                authentication = FirebaseAuth(activity.applicationContext,
                        mailEditText.text.toString(), passworkdEditText.text.toString())
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

        signOutButton.setOnClickListener {
            authentication?.signOut(this)
        }
    }

    fun baseSignInListener() {
        progressDialog = ProgressDialog.show(activity, "Loading", "Wait while user is authenticated...")
        authentication?.signIn(this)
    }

    fun checkUserData(mail: String, password: String): Boolean {
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
            AlertDialog.Builder(activity).setMessage(R.string.empty_user_data).create().show()
            return false
        }
        return true
    }

}
