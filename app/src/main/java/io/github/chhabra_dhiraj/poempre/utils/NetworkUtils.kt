package io.github.chhabra_dhiraj.poempre.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils private constructor (context: Context) {
    private val mNetworkInfo: NetworkInfo?

    fun isOnline(): Boolean {
        return mNetworkInfo?.isConnected ?: false
    }

    companion object {
        // Whether the device is connected to the network
        var isNetworkConnected = true

        private var nInstance: NetworkUtils? = null

        @Synchronized
        fun initializeInstance(context: Context) {
            if (nInstance == null) {
                nInstance = NetworkUtils(context)
            }
        }

        @get:Synchronized
        val instance: NetworkUtils?
            get() {
                checkNotNull(nInstance) {
                    NetworkUtils::class.java.simpleName +
                            " is not initialized, call initializeInstance(..) method first."
                }
                return nInstance
            }
    }

    init {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        mNetworkInfo = connMgr.activeNetworkInfo
    }

}