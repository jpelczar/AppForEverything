package io.jpelczar.appforeverything.commons

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {

    fun getPreferences(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun getEditablePreferences(context: Context, name: String): SharedPreferences.Editor = getPreferences(context, name).edit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: String) = getEditablePreferences(context, sharedPrefName).putString(key, value).commit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: Long) = getEditablePreferences(context, sharedPrefName).putLong(key, value).commit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: Int) = getEditablePreferences(context, sharedPrefName).putInt(key, value).commit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: Float) = getEditablePreferences(context, sharedPrefName).putFloat(key, value).commit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: Boolean) = getEditablePreferences(context, sharedPrefName).putBoolean(key, value).commit()

    fun persist(context: Context, sharedPrefName: String, key: String, value: Set<String>) = getEditablePreferences(context, sharedPrefName).putStringSet(key, value).commit()

    fun load(context: Context, sharedPrefName: String, key: String): Any? = getPreferences(context, sharedPrefName).all[key]

    fun loadString(context: Context, sharedPrefName: String, key: String): String? = load(context, sharedPrefName, key) as String?

    fun loadLong(context: Context, sharedPrefName: String, key: String): Long? = load(context, sharedPrefName, key) as Long?

    fun loadInt(context: Context, sharedPrefName: String, key: String): Int? = load(context, sharedPrefName, key) as Int?

    fun loadFloat(context: Context, sharedPrefName: String, key: String): Float? = load(context, sharedPrefName, key) as Float?

    fun loadBoolean(context: Context, sharedPrefName: String, key: String): Boolean? = load(context, sharedPrefName, key) as Boolean?

    fun loadStringSet(context: Context, sharedPrefName: String, key: String): Set<String>? = load(context, sharedPrefName, key) as Set<String>?
            ?: HashSet()

    fun remove(context: Context, sharedPrefName: String, key: String) = getPreferences(context, sharedPrefName).edit().remove(key).apply()

}
