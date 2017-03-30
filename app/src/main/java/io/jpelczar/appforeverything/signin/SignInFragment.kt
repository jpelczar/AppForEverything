package io.jpelczar.appforeverything.signin


import android.content.Intent
import android.os.Bundle
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
import io.jpelczar.appforeverything.module.auth.FirebaseAuth
import io.jpelczar.appforeverything.module.auth.GoogleAuth
import java.util.*

class SignInFragment : BaseFragment() {

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

    var authentication: Authentication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authentication = GoogleAuth(activity)
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
        if (authentication is GoogleAuth) {
            (authentication as GoogleAuth).handleResult(requestCode, resultCode, data)
        }
    }

    fun setUpListeners() {
        signUpButton.setOnClickListener {
            authentication = FirebaseAuth(activity.applicationContext)
            authentication?.signUp(Account(UUID.randomUUID().toString(), mailEditText.text.toString(),
                    passworkdEditText.text.toString(), Account.MAIL), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

        signInButton.setOnClickListener {
            authentication = FirebaseAuth(activity.applicationContext)
            authentication?.signIn(Account("", mailEditText.text.toString(),
                    passworkdEditText.text.toString(), Account.MAIL), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

        signInFacebook.setOnClickListener { }

        signInGoogle.setOnClickListener {
            authentication?.signIn(Account("", mailEditText.text.toString(),
                    passworkdEditText.text.toString(), Account.GOOGLE), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }

        signOutButton.setOnClickListener {
            authentication?.signOut(object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    val displayText = "${Authentication.translateAuthState(state)} - $message"
                    authOutTextView.text = displayText
                    L.d(displayText)
                }
            })
        }
    }

}
