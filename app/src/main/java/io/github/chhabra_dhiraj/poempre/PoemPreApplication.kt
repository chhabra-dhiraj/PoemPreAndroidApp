package io.github.chhabra_dhiraj.poempre

import android.app.Application
import timber.log.Timber

class PoemPreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}