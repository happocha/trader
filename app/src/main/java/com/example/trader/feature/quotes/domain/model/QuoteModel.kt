package com.example.trader.feature.quotes.domain.model

import java.math.BigDecimal

class QuoteModel(
    val c: String?,
    var pcp: BigDecimal?,
    var ltr: String?,
    var name: String?,
    var ltp: BigDecimal?,
    var chg: BigDecimal?,
)
