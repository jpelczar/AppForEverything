package io.jpelczar.appforeverything

import android.os.Bundle
import io.jpelczar.appforeverything.core.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
