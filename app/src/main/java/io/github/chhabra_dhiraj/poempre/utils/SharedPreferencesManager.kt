package io.github.chhabra_dhiraj.poempre.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {
    private val mPref: SharedPreferences

    var sessionId: String?
        get() = mPref.getString(SESSION_KEY_VALUE, null)
        set(value) {
            mPref.edit()
                .putString(SESSION_KEY_VALUE, value)
                .apply()
        }

    var userId: Int
        get() = mPref.getInt(USER_ID_KEY_VALUE, -1)
        set(value) {
            mPref.edit()
                .putInt(USER_ID_KEY_VALUE, value)
                .apply()
        }

    var email: String?
        get() = mPref.getString(EMAIL_KEY_VALUE, null)
        set(value) {
            mPref.edit()
                .putString(EMAIL_KEY_VALUE, value)
                .apply()
        }

    var firstName: String?
        get() = mPref.getString(FIRST_NAME_KEY_VALUE, null)
        set(value) {
            mPref.edit()
                .putString(FIRST_NAME_KEY_VALUE, value)
                .apply()
        }

    var lastName: String?
        get() = mPref.getString(LAST_NAME_KEY_VALUE, null)
        set(value) {
            mPref.edit()
                .putString(LAST_NAME_KEY_VALUE, value)
                .apply()
        }

    var imageUrl: String?
        get() = mPref.getString(IMAGE_URL_KEY_VALUE, null)
        set(value) {
            mPref.edit()
                .putString(IMAGE_URL_KEY_VALUE, value)
                .apply()
        }

    fun remove(key: String?) {
        mPref.edit()
            .remove(key)
            .apply()
    }

    fun clear(): Boolean {
        return mPref.edit()
            .clear()
            .commit()
    }

    companion object {
        private const val PREF_NAME = "io.github.chhabra_dhiraj.poempre.POEM_PRE_APP_PREF"

        private var sInstance: SharedPreferencesManager? = null

        @Synchronized
        fun initializeInstance(context: Context) {
            if (sInstance == null) {
                sInstance = SharedPreferencesManager(context)
            }
        }

        @get:Synchronized
        val instance: SharedPreferencesManager?
            get() {
                checkNotNull(sInstance) {
                    SharedPreferencesManager::class.java.simpleName +
                            " is not initialized, call initializeInstance(..) method first."
                }
                return sInstance
            }
    }

    init {
        mPref = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}