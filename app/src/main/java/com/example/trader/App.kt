package com.example.trader

import android.app.Application
import com.example.trader.feature.quotes.di.*
import com.example.trader.di.*

class App : Application() {
    val quotesWebService by lazy(::provideWebService)
    val quotesRepository by lazy(::provideQuotesRepository)
    val quotesMapper by lazy(::provideQuotesMapper)
    val quotesUseCase by lazy(::provideQuotesUseCase)
    val quotesConverter by lazy(::provideQuotesConverter)
    val gson by lazy(::provideGson)
    val networkChecker by lazy(::provideNetworkChecker)

}