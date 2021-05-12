package com.example.trader.feature.quotes.data

import com.example.trader.feature.quotes.domain.model.QuoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface QuotesRepository {
    fun startSocket()
    fun stopSocket()

    suspend fun getQuotes(): Flow<QuoteModel?>
}

class QuotesRepositoryImpl(
    private val quotesWebServicesProvider: QuotesWebServicesProvider,
    private val mapper: QuotesMapper,
    private val quotesController: QuotesController
) : QuotesRepository {

    override fun startSocket() {
        quotesWebServicesProvider.startSocket()
    }

    override fun stopSocket() {
        quotesWebServicesProvider.stopSocket()
    }

    override suspend fun getQuotes(): Flow<QuoteModel?> =
        quotesController.state.map { response ->
            when (response) {
                is QuotesResponse.Data -> mapper.mapValue(response.text)
                is QuotesResponse.Error -> throw response.throwable
            }
        }
}
