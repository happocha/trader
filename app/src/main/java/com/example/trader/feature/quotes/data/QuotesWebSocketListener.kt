package com.example.trader.feature.quotes.data

import android.util.Log
import com.example.trader.feature.quotes.data.QuotesWebServicesProviderImpl.Companion.NORMAL_CLOSURE_STATUS
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class QuotesWebSocketListener(
    private val quotesController: QuotesController
) : WebSocketListener() {

    companion object {
        private const val QUOTATION_LIST =
            "[\"quotes\",[\"RSTI\",\"GAZP\",\"MRKZ\",\"RUAL\",\"HYDR\",\"MRKS\",\"SBER\",\"FEES\",\"TGKA\",\"VTBR\",\"ANH.US\",\"VICL.US\",\"BURG.US\",\"NBL.US\",\"YETI.US\",\"WSFS.US\",\"NIO.US\",\"DXC.US\",\"MIC.US\",\"HSBC.US\",\"EXPN.EU\",\"GSK.EU\",\"SHP.EU\",\"MAN.EU\",\"DB1.EU\",\"MUV2.EU\",\"TATE.EU\",\"KGF.EU\",\"MGGT.EU\",\"SGGD.EU\"]]"
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send(QUOTATION_LIST)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocketListener", text)
        quotesController.setValue(QuotesResponse.Data(text))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("WebSocketListener", "onClosing")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        quotesController.reset()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocketListener", t.localizedMessage.orEmpty())
        quotesController.setValue(QuotesResponse.Error(t))
    }
}
