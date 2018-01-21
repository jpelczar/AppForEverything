package io.jpelczar.appforeverything.module.auth

import android.content.Context
import android.net.Uri
import android.text.format.DateUtils
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.loadString
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.loadStringSet
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.persist
import io.jpelczar.appforeverything.data.Account


object AccountPersister {

    private const val USER_SHARED_PREF_NAME = "USER_SHARED_PREF_NAME"
    private const val USER_EXPIRED_SHARED_PREF_KEY = "USER_EXPIRED_SHARED_PREF_KEY"
    private const val EXPIRED_PERIOD = 7 * DateUtils.DAY_IN_MILLIS
    private const val ID_KEY = "ID_KEY"
    private const val MAIL_KEY = "MAIL_KEY"
    private const val NAME_KEY = "NAME_KEY"
    private const val TYPE_KEY = "TYPE_KEY"
    private const val PHOTO_URL_KEY = "PHOTO_URL_KEY"
    private const val PROVIDERS_KEY = "PROVIDERS_KEY"

    fun persist(context: Context, account: Account) {
        persist(context, USER_SHARED_PREF_NAME, ID_KEY, account.id)
        persist(context, USER_SHARED_PREF_NAME, MAIL_KEY, account.mail)
        persist(context, USER_SHARED_PREF_NAME, NAME_KEY, account.name)
        persist(context, USER_SHARED_PREF_NAME, TYPE_KEY, account.type)
        persist(context, USER_SHARED_PREF_NAME, PHOTO_URL_KEY, (account.photoUrl ?: "").toString())
        persist(context, USER_SHARED_PREF_NAME, PROVIDERS_KEY, account.providers.toSet())
    }

    fun load(context: Context): Account {
        val account = Account()
        account.id = loadString(context, USER_SHARED_PREF_NAME, ID_KEY) ?: ""
        account.mail = loadString(context, USER_SHARED_PREF_NAME, MAIL_KEY) ?: ""
        account.name = loadString(context, USER_SHARED_PREF_NAME, NAME_KEY) ?: ""
        account.type = loadString(context, USER_SHARED_PREF_NAME, TYPE_KEY) ?: ""
        account.photoUrl = Uri.parse(loadString(context, USER_SHARED_PREF_NAME, PHOTO_URL_KEY))
        account.providers.addAll(loadStringSet(context, USER_SHARED_PREF_NAME, PROVIDERS_KEY)
                ?: ArrayList())

        return account
    }

    fun clean(context: Context) {
        SharedPreferencesUtil.getEditablePreferences(context, USER_SHARED_PREF_NAME).clear().apply()
    }

    fun refreshValidationPeriod(context: Context) {
        SharedPreferencesUtil.persist(context, USER_SHARED_PREF_NAME, USER_EXPIRED_SHARED_PREF_KEY, System.currentTimeMillis())
    }

    fun isAccountExpired(context: Context): Boolean = (SharedPreferencesUtil.loadLong(context, USER_SHARED_PREF_NAME,
            USER_EXPIRED_SHARED_PREF_KEY) ?: 0) < (System.currentTimeMillis() - EXPIRED_PERIOD)
}