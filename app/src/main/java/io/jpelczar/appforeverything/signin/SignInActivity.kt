package io.jpelczar.appforeverything.signin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.auth.FirebaseAuth
import java.util.*

class SignInActivity : BaseActivity() {

    @BindView(R.id.sign_up_button)
    lateinit var signUpButton: Button

    @BindView(R.id.sign_in_button)
    lateinit var signInButton: Button

    @BindView(R.id.sign_out_button)
    lateinit var signOutButton: Button

    @BindView(R.id.mail_edit_text)
    lateinit var mailEditText: EditText

    @BindView(R.id.password_edit_text)
    lateinit var passworkdEditText: EditText

    @BindView(R.id.auth_result_text_view)
    lateinit var authOutTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        ButterKnife.bind(this)
        authentication = FirebaseAuth(applicationContext)

        signUpButton.setOnClickListener {
            authentication.signUp(Account(UUID.randomUUID().toString(), mailEditText.text.toString(),
                    passworkdEditText.text.toString(), Account.MAIL), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

        signInButton.setOnClickListener {
            authentication.signIn(Account("", mailEditText.text.toString(),
                    passworkdEditText.text.toString(), Account.MAIL), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

        signOutButton.setOnClickListener {
            authentication.signOut(object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

    }
}
