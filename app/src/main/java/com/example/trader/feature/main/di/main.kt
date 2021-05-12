package com.example.trader.feature.main.di

import androidx.lifecycle.ViewModelProvider
import com.example.trader.common.ViewModelFactory
import com.example.trader.common.extension.app
import com.example.trader.feature.main.MainActivity
import com.example.trader.feature.main.MainViewModel
import com.example.trader.feature.main.MainViewModelImpl

fun MainActivity.provideMainViewModel(): MainViewModel =
    ViewModelProvider(
        this,
        ViewModelFactory {
            MainViewModelImpl(
                quotesSocketUseCase = app.quotesSocketUseCase,
                networkChecker = app.networkChecker
            )
        })[MainViewModelImpl::class.java]