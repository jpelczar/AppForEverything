package io.jpelczar.appforeverything.module

import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.jpelczar.appforeverything.R
import io.jpelczar.core.commons.LogPrefix
import io.jpelczar.appforeverything.core.BaseDrawerActivity
import io.jpelczar.core.commons.L

abstract class ModuleBaseActivity : BaseDrawerActivity() {

    override fun initNavigationHeader() {
        val headerView = LayoutInflater.from(this).inflate(R.layout.header_drawer_module, headerContainer, true)
        val profileImageView = headerView.findViewById<ImageView>(R.id.profile_image)
        val profileNameView = headerView.findViewById<TextView>(R.id.profile_name)

        L.d(LogPrefix.UI, "Profile name: ${currentAccount.name}, Profile image: ${currentAccount.photoUrl}")
        profileNameView.text = currentAccount.name
        if (!TextUtils.isEmpty((currentAccount.photoUrl ?: "").toString())) {
            Glide.with(this).load(currentAccount.photoUrl).into(profileImageView)
        }
    }
}
