package com.example.trader.feature.quotes.di

import androidx.lifecycle.ViewModelProvider
import com.example.trader.App
import com.example.trader.common.ViewModelFactory
import com.example.trader.common.extension.app
import com.example.trader.feature.quotes.data.*
import com.example.trader.feature.quotes.domain.QuotesSocketUseCase
import com.example.trader.feature.quotes.domain.QuotesSocketUseCaseImpl
import com.example.trader.feature.quotes.domain.QuotesUseCase
import com.example.trader.feature.quotes.domain.QuotesUseCaseImpl
import com.example.trader.feature.quotes.presentation.*

fun App.provideQuotesController(): QuotesController =
    QuotesControllerImpl()

fun App.provideWebService(): QuotesWebServicesProvider =
    QuotesWebServicesProviderImpl(quotesController)

fun App.provideQuotesMapper(): QuotesMapper =
    QuotesMapperImpl(gson)

fun App.provideQuotesRepository(): QuotesRepository =
    QuotesRepositoryImpl(quotesWebService, quotesMapper, quotesController)

fun App.provideQuotesSocketUseCase(): QuotesSocketUseCase =
    QuotesSocketUseCaseImpl(quotesRepository)

fun App.provideQuotesUseCase(): QuotesUseCase =
    QuotesUseCaseImpl(quotesRepository)

fun App.provideQuotesConverter(): QuotesConverter =
    QuotesConverterImpl()

fun QuotesFragment.provideQuotesViewModel(): QuotesViewModel =
    ViewModelProvider(
        this,
        ViewModelFactory {
            QuotesViewModelImpl(
                quotesUseCase = app.quotesUseCase,
                quotesConverter = app.quotesConverter
            )
        })[QuotesViewModelImpl::class.java]
