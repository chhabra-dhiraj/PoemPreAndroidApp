package io.github.chhabra_dhiraj.poempre.androidsysteminteraction.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.github.chhabra_dhiraj.poempre.utils.NetworkUtils.Companion.isNetworkConnected

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        isNetworkConnected = networkInfo?.isConnected ?: false
    }
}