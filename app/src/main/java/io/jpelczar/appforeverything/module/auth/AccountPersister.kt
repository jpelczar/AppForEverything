package io.jpelczar.appforeverything.module.auth

import android.content.Context
import android.net.Uri
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.loadString
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.loadStringSet
import io.jpelczar.appforeverything.commons.SharedPreferencesUtil.persist
import io.jpelczar.appforeverything.data.Account


object AccountPersister {

    private const val USER_SHARD_PREF = "USER_SHARD_PREF"
    private const val ID_KEY = "ID_KEY"
    private const val MAIL_KEY = "MAIL_KEY"
    private const val NAME_KEY = "NAME_KEY"
    private const val TYPE_KEY = "TYPE_KEY"
    private const val PHOTO_URL_KEY = "PHOTO_URL_KEY"
    private const val PROVIDERS_KEY = "PROVIDERS_KEY"

    fun persist(context: Context, account: Account) {
        persist(context, USER_SHARD_PREF, ID_KEY, account.id)
        persist(context, USER_SHARD_PREF, MAIL_KEY, account.mail)
        persist(context, USER_SHARD_PREF, NAME_KEY, account.name)
        persist(context, USER_SHARD_PREF, TYPE_KEY, account.type)
        persist(context, USER_SHARD_PREF, PHOTO_URL_KEY, (account.photoUrl ?: "").toString())
        persist(context, USER_SHARD_PREF, PROVIDERS_KEY, account.providers.toSet())
    }

    fun load(context: Context): Account {
        val account = Account()
        account.id = loadString(context, USER_SHARD_PREF, ID_KEY)
        account.mail = loadString(context, USER_SHARD_PREF, MAIL_KEY)
        account.name = loadString(context, USER_SHARD_PREF, NAME_KEY)
        account.type = loadString(context, USER_SHARD_PREF, TYPE_KEY)
        account.photoUrl = Uri.parse(loadString(context, USER_SHARD_PREF, PHOTO_URL_KEY))
        account.providers.addAll(loadStringSet(context, USER_SHARD_PREF, PROVIDERS_KEY)
                ?: ArrayList())

        return account
    }
}