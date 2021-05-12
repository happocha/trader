package com.example.trader.feature.quotes.domain

import com.example.trader.feature.quotes.data.QuotesRepository
import com.example.trader.feature.quotes.domain.model.QuoteModel
import kotlinx.coroutines.flow.Flow

interface QuotesUseCase {
    suspend fun getQuotes(): Flow<QuoteModel?>
}

class QuotesUseCaseImpl(
    private val repository: QuotesRepository
) : QuotesUseCase {

    override suspend fun getQuotes(): Flow<QuoteModel?> =
        repository.getQuotes()
}