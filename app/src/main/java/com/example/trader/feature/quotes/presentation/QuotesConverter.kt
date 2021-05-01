package com.example.trader.feature.quotes.presentation

import com.example.trader.R
import com.example.trader.common.extension.lowercase
import com.example.trader.feature.quotes.domain.model.QuoteModel
import com.example.trader.feature.quotes.presentation.model.QuoteViewData
import java.util.*
import kotlin.collections.ArrayList

interface QuotesConverter {

    fun convert(
        map: HashMap<String, QuoteModel>,
        cachedMap: HashMap<String, QuoteModel>
    ): List<QuoteViewData>
}

class QuotesConverterImpl : QuotesConverter {

    companion object {
        private const val BASE_IMAGE_URL = "https://tradernet.ru/logos/get-logo-by-ticker?ticker="
    }

    override fun convert(
        map: HashMap<String, QuoteModel>,
        cachedMap: HashMap<String, QuoteModel>
    ): List<QuoteViewData> {
        val items = ArrayList<QuoteViewData>()
        map.forEach { item ->
            val cachedItem = cachedMap[item.key]
            val currentItem = item.value

            val ltr = currentItem.ltr ?: cachedItem?.ltr
            val name = currentItem.name ?: cachedItem?.name
            val ltp = currentItem.ltp ?: cachedItem?.ltp
            val pcp = currentItem.pcp ?: cachedItem?.pcp

            val background = cachedItem?.chg?.let {
                when {
                    currentItem.chg != null && it < currentItem.chg -> R.drawable.bg_green
                    currentItem.chg != null && it > currentItem.chg -> R.drawable.bg_red
                    else -> null
                }
            }

            val chg = currentItem.chg ?: cachedItem?.chg

            val textColor = when {
                background != null -> R.color.white
                chg != null && chg.toDouble() < 0 -> R.color.red
                else -> R.color.green
            }

            val cachedModel = QuoteModel(
                c = currentItem.c,
                pcp = pcp,
                ltr = ltr,
                name = name,
                ltp = ltp,
                chg = chg
            )

            cachedMap[item.key] = cachedModel

            items.add(
                QuoteViewData(
                    imageUrl = "$BASE_IMAGE_URL${item.value.c?.lowercase}",
                    title = item.value.c.orEmpty(),
                    subtitle = "$ltr|$name",
                    lastPrice = "$ltp ( $pcp )",
                    changePrice = "$chg%",
                    changePriceTextColor = textColor,
                    changePriceBackground = background
                )
            )
        }
        return items
    }
}
