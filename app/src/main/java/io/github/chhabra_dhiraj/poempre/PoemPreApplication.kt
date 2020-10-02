package io.github.chhabra_dhiraj.poempre

import android.app.Application
import io.github.chhabra_dhiraj.poempre.utils.NetworkUtils
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import timber.log.Timber

class PoemPreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.initializeInstance(this)
        NetworkUtils.initializeInstance(this)
        Timber.plant(Timber.DebugTree())
    }
}