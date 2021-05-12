package com.example.trader.feature.quotes.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

interface QuotesWebServicesProvider {
    fun startSocket()
    fun stopSocket()
}

class QuotesWebServicesProviderImpl(
    private val quotesController: QuotesController
) : QuotesWebServicesProvider {

    companion object {
        private const val BASE_URL = "wss://wss.tradernet.ru"
        const val NORMAL_CLOSURE_STATUS = 1000
    }

    private var _webSocket: WebSocket? = null

    private val socketOkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .hostnameVerifier { _, _ -> true }
        .retryOnConnectionFailure(true)
        .build()

    private var _webSocketListener: QuotesWebSocketListener? = null

    override fun startSocket() =
        with(QuotesWebSocketListener(quotesController)) {
            startSocket(this)
        }

    private fun startSocket(quotesWebSocketListener: QuotesWebSocketListener) {
        _webSocketListener = quotesWebSocketListener
        _webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url(BASE_URL).build(),
            quotesWebSocketListener
        )
    }

    override fun stopSocket() {
        try {
            _webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            _webSocket = null
            _webSocketListener = null
            quotesController.reset()
            socketOkHttpClient.dispatcher.executorService.shutdown()
        } catch (ex: Exception) {
            Log.e("WebServicesProviderImpl", ex.message, ex)
        }
    }
}
