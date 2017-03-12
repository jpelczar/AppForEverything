package io.jpelczar.appforeverything.commons

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    
    lateinit var context: Context

    fun getPreferences(name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun getEditablePreferences(name: String): SharedPreferences.Editor = getPreferences(name).edit().clear()

    fun persist(sharedPrefName: String, key: String, value: String) = getEditablePreferences(sharedPrefName).putString(key, value).commit()

    fun persist(sharedPrefName: String, key: String, value: Long) = getEditablePreferences(sharedPrefName).putLong(key, value).commit()

    fun persist(sharedPrefName: String, key: String, value: Int) = getEditablePreferences(sharedPrefName).putInt(key, value).commit()

    fun persist(sharedPrefName: String, key: String, value: Float) = getEditablePreferences(sharedPrefName).putFloat(key, value).commit()

    fun persist(sharedPrefName: String, key: String, value: Boolean) = getEditablePreferences(sharedPrefName).putBoolean(key, value).commit()

    fun persist(sharedPrefName: String, key: String, value: Set<String>) = getEditablePreferences(sharedPrefName).putStringSet(key, value).commit()

    fun load(sharedPrefName: String, key: String): Any? = getPreferences(sharedPrefName).all[key]

    fun loadString(sharedPrefName: String, key: String): String = load(sharedPrefName, key) as String

    fun loadLong(sharedPrefName: String, key: String): Long? = load(sharedPrefName, key) as Long?

    fun loadInt(sharedPrefName: String, key: String): Int? = load(sharedPrefName, key) as Int?

    fun loadFloat(sharedPrefName: String, key: String): Float? = load(sharedPrefName, key) as Float?

    fun loadBoolean(sharedPrefName: String, key: String): Boolean? = load(sharedPrefName, key) as Boolean?

    fun loadStringSet(sharedPrefName: String, key: String): Set<String>? = load(sharedPrefName, key) as Set<String>? ?: HashSet<String>()

    fun remove(sharedPrefName: String, key: String) = getPreferences(sharedPrefName).edit().remove(key).commit()

}
