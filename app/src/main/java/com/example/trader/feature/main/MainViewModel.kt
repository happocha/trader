package com.example.trader.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.trader.common.NetworkChecker
import com.example.trader.common.SingleLiveEvent
import com.example.trader.feature.quotes.domain.QuotesSocketUseCase

interface MainViewModel {
    val showErrorConnectionMessage: LiveData<Boolean>

    fun onNetworkStateChanged()
}

class MainViewModelImpl(
    private val quotesSocketUseCase: QuotesSocketUseCase,
    private val networkChecker: NetworkChecker
) : MainViewModel, ViewModel() {

    override val showErrorConnectionMessage = SingleLiveEvent<Boolean>()

    override fun onNetworkStateChanged() {
        showErrorConnectionMessage.value = networkChecker.isNetworkAvailable().not()
        openSocket()
    }

    private fun openSocket() {
        quotesSocketUseCase.startSocket()
    }

    override fun onCleared() {
        quotesSocketUseCase.stopSocket()
        super.onCleared()
    }
}
