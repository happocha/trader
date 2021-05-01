package com.example.trader.feature.quotes.data

import android.util.Log
import com.example.trader.feature.quotes.data.WebServicesProviderImpl.Companion.NORMAL_CLOSURE_STATUS
import com.example.trader.feature.quotes.data.model.SocketResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SocketAbortedException : Exception()

class WebSocketListener : WebSocketListener() {

    companion object {
        private const val QUOTATION_LIST =
            "[\"quotes\",[\"RSTI\",\"GAZP\",\"MRKZ\",\"RUAL\",\"HYDR\",\"MRKS\",\"SBER\",\"FEES\",\"TGKA\",\"VTBR\",\"ANH.US\",\"VICL.US\",\"BURG.US\",\"NBL.US\",\"YETI.US\",\"WSFS.US\",\"NIO.US\",\"DXC.US\",\"MIC.US\",\"HSBC.US\",\"EXPN.EU\",\"GSK.EU\",\"SHP.EU\",\"MAN.EU\",\"DB1.EU\",\"MUV2.EU\",\"TATE.EU\",\"KGF.EU\",\"MGGT.EU\",\"SGGD.EU\"]]"
    }

    val state: MutableSharedFlow<SocketResponse> = MutableSharedFlow()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send(QUOTATION_LIST)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            Log.d("WebSocketListener", text)
            state.emit(SocketResponse(text))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        GlobalScope.launch {
            state.emit(SocketResponse(exception = SocketAbortedException()))
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        state.resetReplayCache()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        GlobalScope.launch {
            state.emit(SocketResponse(exception = t))
        }
    }
}
