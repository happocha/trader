package com.example.trader.common

import android.app.Application
import com.example.trader.common.extension.systemConnectivityManager

interface NetworkChecker {

    fun isNetworkAvailable(): Boolean

}

class NetworkCheckerImpl(app: Application) : NetworkChecker {

    private val connectivityManager = app.systemConnectivityManager

    @Suppress("DEPRECATION")
    override fun isNetworkAvailable(): Boolean =
        connectivityManager.activeNetworkInfo?.isConnected ?: false
}