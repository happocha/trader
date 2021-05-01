package com.example.trader.feature.quotes.domain

import com.example.trader.feature.quotes.data.QuotesRepository
import com.example.trader.feature.quotes.domain.model.QuoteModel
import kotlinx.coroutines.flow.Flow

interface QuotesUseCase {
    suspend fun startSocket(): Flow<QuoteModel?>
    fun stopSocket()
}

class QuotesUseCaseImpl(
    private val repository: QuotesRepository
) : QuotesUseCase {

    override suspend fun startSocket(): Flow<QuoteModel?> =
        repository.startSocket()

    override fun stopSocket() = repository.stopSocket()
}