package io.jpelczar.appforeverything.data

import android.net.Uri
import android.support.annotation.StringDef
import com.google.firebase.auth.*
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

    constructor(account: Account) : this(account.name, account.mail, account.type)

    @DatabaseField
    lateinit var id: String

    @DatabaseField
    lateinit var name: String

    @DatabaseField
    lateinit var mail: String

    @DatabaseField
    @Type
    lateinit var type: String

    @DatabaseField
    var photoUrl: Uri? = null

    @DatabaseField
    val providers: MutableList<String> = ArrayList()

    fun fill(account: Account) {
        this.id = account.id
        this.type = account.type
        this.mail = account.mail
        this.name = account.name
        this.photoUrl = account.photoUrl
        this.providers.clear()
        this.providers.addAll(account.providers)
    }

    fun setFromFirebase(user: FirebaseUser): Account {
        this.id = user.uid
        this.name = user.displayName ?: ""
        this.mail = user.email ?: ""
        this.type = translateProvider(user.providerId)
        user.providers?.forEach { provider -> this.providers.add(translateProvider(provider)) }
        return this
    }

    fun translateProvider(provider: String): String {
        return when (provider) {
            EmailAuthProvider.PROVIDER_ID -> MAIL
            GoogleAuthProvider.PROVIDER_ID -> GOOGLE
            FacebookAuthProvider.PROVIDER_ID -> FACEBOOK
            TwitterAuthProvider.PROVIDER_ID -> TWITTER
            GithubAuthProvider.PROVIDER_ID -> GITHUB
            FirebaseAuthProvider.PROVIDER_ID -> FIREBASE
            else -> NA
        }
    }

    override fun toString(): String {
        return "Account(id='$id', name='$name', mail='$mail', type='$type', photoUrl=$photoUrl, providers=$providers)"
    }

    companion object {
        const val FACEBOOK = "FACEBOOK" //implemented - not used due to firebase issue about account merging
        const val GOOGLE = "GOOGLE"
        const val GITHUB = "GITHUB" //not implemented
        const val TWITTER = "TWITTER" //not implemented
        const val MAIL = "MAIL"
        const val FIREBASE = "FIREBASE" //when user have more than one sign in method
        const val NA = "NA"
    }

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(FACEBOOK, GOOGLE, GITHUB, TWITTER, MAIL, FIREBASE, NA)
    annotation class Type

}