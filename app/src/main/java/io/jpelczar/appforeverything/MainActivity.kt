package io.jpelczar.appforeverything

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import io.jpelczar.appforeverything.commons.L
import io.jpelczar.appforeverything.core.BaseActivity
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.Authentication
import io.jpelczar.appforeverything.module.auth.FirebaseAuth
import java.util.*

class MainActivity : BaseActivity() {

    @BindView(R.id.sign_up_button)
    lateinit var signUpButton: Button

    @BindView(R.id.mail_edit_text)
    lateinit var mailEditText: EditText

    @BindView(R.id.password_edit_text)
    lateinit var passworkdEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        authentication = FirebaseAuth(applicationContext)

        signUpButton.setOnClickListener { v ->
            authentication.sigUp(Account(UUID.randomUUID().toString(), mailEditText.text.toString(), Account.MAIL),
                    passworkdEditText.text.toString(), object : Authentication.Callback {
                override fun onResult(state: Long, message: String?) {
                    L.d("$state - $message")
                }
            })
        }

    }
}
