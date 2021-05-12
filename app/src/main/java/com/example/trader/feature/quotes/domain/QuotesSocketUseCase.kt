package com.example.trader.feature.quotes.domain

import com.example.trader.feature.quotes.data.QuotesRepository

interface QuotesSocketUseCase {
    fun startSocket()
    fun stopSocket()
}

class QuotesSocketUseCaseImpl(
    private val repository: QuotesRepository
) : QuotesSocketUseCase {

    override fun startSocket() = repository.startSocket()

    override fun stopSocket() = repository.stopSocket()
}
