package com.example.trader.common.extension

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

val Context.systemConnectivityManager: ConnectivityManager
    get() = getAndroidSystemService(Context.CONNECTIVITY_SERVICE)

private inline fun <reified T> Context.getAndroidSystemService(name: String) =
    getSystemService(name) as T
