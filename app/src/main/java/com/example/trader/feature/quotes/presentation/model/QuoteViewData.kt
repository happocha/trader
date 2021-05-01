package com.example.trader.feature.quotes.presentation.model

import androidx.annotation.DrawableRes

data class QuoteViewData(
    val imageUrl: String,
    val title: String,
    val subtitle: String,
    val lastPrice: String,
    val changePrice: String,
    val changePriceTextColor: Int,
    @DrawableRes val changePriceBackground: Int?
)
