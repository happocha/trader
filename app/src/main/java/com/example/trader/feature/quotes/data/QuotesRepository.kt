package com.example.trader.feature.quotes.data

import com.example.trader.feature.quotes.domain.model.QuoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface QuotesRepository {
    suspend fun startSocket(): Flow<QuoteModel?>
    fun stopSocket()
}

class QuotesRepositoryImpl(
    private val webServicesProvider: WebServicesProvider,
    private val mapper: QuotesMapper
) : QuotesRepository {

    override suspend fun startSocket(): Flow<QuoteModel?> =
        webServicesProvider.startSocket()
            .map { response ->
                response.exception?.let {
                    throw it
                }
                mapper.mapValue(response.text)
            }

    override fun stopSocket() {
        webServicesProvider.stopSocket()
    }
}
