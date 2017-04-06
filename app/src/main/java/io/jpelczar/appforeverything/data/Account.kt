package io.jpelczar.appforeverything.data

import android.support.annotation.StringDef
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "account")
class Account : BaseStoredData {

    constructor()

    constructor(type: String) : super() {
        this.type = type
    }

    constructor(name: String, mail: String, type: String) : super() {
        this.name = name
        this.mail = mail
        this.type = type
    }

    constructor(name: String, mail: String, password: String, type: String) : super() {
        this.name = name
        this.mail = mail
        this.type = type
        this.password = password
    }


    @DatabaseField
    lateinit var name: String

    @DatabaseField
    lateinit var mail: String

    @DatabaseField
    @Type
    lateinit var type: String

    lateinit var password: String

    companion object {
        const val FACEBOOK = "FACEBOOK"
        const val GOOGLE = "GOOGLE"
        const val GITHUB = "GITHUB"
        const val TWITTER = "TWITTER"
        const val MAIL = "MAIL"
    }

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(FACEBOOK, GOOGLE, GITHUB, TWITTER, MAIL)
    annotation class Type

}