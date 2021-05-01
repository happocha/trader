package com.example.trader.feature.quotes.data

import android.util.Log
import com.example.trader.feature.quotes.data.model.SocketResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

interface WebServicesProvider {
    fun startSocket(): MutableSharedFlow<SocketResponse>
    fun stopSocket()
}

class WebServicesProviderImpl : WebServicesProvider {

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

    private var _webSocketListener: WebSocketListener? = null

    override fun startSocket(): MutableSharedFlow<SocketResponse> =
        with(WebSocketListener()) {
            startSocket(this)
            this@with.state
        }

    fun startSocket(webSocketListener: WebSocketListener) {
        _webSocketListener = webSocketListener
        _webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url(BASE_URL).build(),
            webSocketListener
        )
    }

    override fun stopSocket() {
        try {
            _webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            _webSocket = null
            _webSocketListener?.state?.resetReplayCache()
            _webSocketListener = null
            socketOkHttpClient.dispatcher.executorService.shutdown()
        } catch (ex: Exception) {
            Log.e("WebServicesProviderImpl", ex.message, ex)
        }
    }
}
