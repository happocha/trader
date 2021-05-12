package com.example.trader.feature.quotes.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface QuotesController {
    val state: Flow<QuotesResponse>

    fun setValue(response: QuotesResponse)
    fun reset()
}

class QuotesControllerImpl : QuotesController {

    private val stateFlow: MutableSharedFlow<QuotesResponse> = MutableSharedFlow()

    override val state: Flow<QuotesResponse> = stateFlow

    override fun setValue(response: QuotesResponse) {
        GlobalScope.launch {
            stateFlow.emit(response)
        }
    }

    override fun reset() {
        stateFlow.resetReplayCache()
    }
}

sealed class QuotesResponse {
    class Data(val text: String?) : QuotesResponse()
    class Error(val throwable: Throwable) : QuotesResponse()
}
