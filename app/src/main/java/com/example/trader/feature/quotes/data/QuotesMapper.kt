package com.example.trader.feature.quotes.data

import com.example.trader.feature.quotes.domain.model.QuoteModel
import com.google.gson.Gson

interface QuotesMapper {

    fun mapValue(text: String?): QuoteModel?
}

class QuotesMapperImpl(
    private val gson: Gson
) : QuotesMapper {

    override fun mapValue(text: String?): QuoteModel? =
        text?.substringAfter('{')?.substringBefore('}')?.let {
            gson.fromJson(
                "{$it}",
                QuoteModel::class.java
            )
        }
}
