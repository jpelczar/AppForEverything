package io.jpelczar.appforeverything.core

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.StartActivity
import io.jpelczar.appforeverything.core.injection.activity.ActivityModule
import io.jpelczar.appforeverything.core.injection.activity.DaggerActivityComponent
import io.jpelczar.appforeverything.data.Account
import io.jpelczar.appforeverything.module.auth.Authentication


abstract class BaseDrawerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.navigation)
    lateinit var navigation: NavigationView

    lateinit var headerContainer: FrameLayout

    private var drawerToggle: ActionBarDrawerToggle? = null

    abstract fun getLayoutId(): Int

    abstract fun initNavigationHeader()

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        View.inflate(this, getLayoutId(), findViewById(R.id.activity_container))

        ButterKnife.bind(this)
        inject()

        activityComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .applicationComponent(App.applicationComponent).build()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }
        }

        drawerToggle?.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle!!)
        drawerToggle?.syncState()

        headerContainer = navigation.getHeaderView(0) as FrameLayout
        navigation.setNavigationItemSelectedListener(this)

        initNavigationHeader()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle?.syncState()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (drawerToggle != null) drawerLayout.removeDrawerListener(drawerToggle!!)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                Authentication.getAuthenticatorForAccount(this, currentAccount)?.signOut(object : Authentication.Callback {
                    override fun onResult(state: Long, message: String?, account: Account?) {
                        when (state) {
                            Authentication.SIGN_OUT_SUCCESS -> {
                                Toast.makeText(this@BaseDrawerActivity, R.string.user_sign_out, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@BaseDrawerActivity, StartActivity::class.java))
                            }
                            Authentication.SIGN_OUT_FAIL -> {
                                Toast.makeText(this@BaseDrawerActivity, R.string.user_not_sign_out, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                false
            }
            else -> false
        }
    }
}