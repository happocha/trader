package com.example.trader.di

import com.example.trader.App
import com.example.trader.common.NetworkChecker
import com.example.trader.common.NetworkCheckerImpl
import com.google.gson.Gson

fun App.provideGson(): Gson = Gson()

fun App.provideNetworkChecker(): NetworkChecker =
    NetworkCheckerImpl(this)