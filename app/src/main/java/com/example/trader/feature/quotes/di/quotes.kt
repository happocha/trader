package com.example.trader.feature.quotes.di

import androidx.lifecycle.ViewModelProvider
import com.example.trader.App
import com.example.trader.common.ViewModelFactory
import com.example.trader.common.extension.app
import com.example.trader.feature.quotes.data.*
import com.example.trader.feature.quotes.domain.QuotesUseCase
import com.example.trader.feature.quotes.domain.QuotesUseCaseImpl
import com.example.trader.feature.quotes.presentation.*
import com.google.gson.Gson

fun App.provideWebService(): WebServicesProvider =
    WebServicesProviderImpl()

fun App.provideQuotesMapper(): QuotesMapper =
    QuotesMapperImpl(gson)

fun App.provideQuotesRepository(): QuotesRepository =
    QuotesRepositoryImpl(quotesWebService, quotesMapper)

fun App.provideQuotesUseCase(): QuotesUseCase =
    QuotesUseCaseImpl(quotesRepository)

fun App.provideQuotesConverter(): QuotesConverter =
    QuotesConverterImpl()

fun QuotesActivity.provideQuotesViewModel(): QuotesViewModel =
    ViewModelProvider(
        this,
        ViewModelFactory {
            QuotesViewModelImpl(
                app.quotesUseCase,
                app.quotesConverter,
                app.networkChecker
            )
        })[QuotesViewModelImpl::class.java]
