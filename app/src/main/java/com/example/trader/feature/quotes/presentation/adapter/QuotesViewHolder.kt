package com.example.trader.feature.quotes.presentation.adapter

import com.example.trader.common.CommonViewHolder
import com.example.trader.common.extension.color
import com.example.trader.common.extension.setSquareImage
import com.example.trader.databinding.ItemQuoteBinding
import com.example.trader.feature.quotes.presentation.model.QuoteViewData

class QuotesViewHolder(
    private val binding: ItemQuoteBinding
) : CommonViewHolder<QuoteViewData>(binding.root) {

    override fun onBind(position: Int, model: QuoteViewData) {
        with(binding) {
            ivQuoteIcon.setSquareImage(model.imageUrl)
            tvQuoteTitle.text = model.title
            tvQuoteSubtitle.text = model.subtitle
            tvQuoteLastPrice.text = model.lastPrice
            tvQuoteChangePrice.apply {
                text = model.changePrice
                setTextColor(tvQuoteChangePrice.context.color(model.changePriceTextColor))
                setBackgroundResource(model.changePriceBackground ?: 0)
            }
        }
    }
}
