package com.example.trader.feature.quotes.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.trader.R
import com.example.trader.common.CommonViewHolder
import com.example.trader.common.extension.inflate
import com.example.trader.databinding.ItemQuoteBinding
import com.example.trader.feature.quotes.presentation.model.QuoteViewData

class QuotesAdapter :
    ListAdapter<QuoteViewData, CommonViewHolder<QuoteViewData>>(QuotesItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<QuoteViewData> =
        QuotesViewHolder(ItemQuoteBinding.bind(parent.inflate(R.layout.item_quote)))

    override fun onBindViewHolder(holder: CommonViewHolder<QuoteViewData>, position: Int) =
        holder.bind(position, getItem(position))


    private class QuotesItemCallback : DiffUtil.ItemCallback<QuoteViewData>() {

        override fun areItemsTheSame(oldItem: QuoteViewData, newItem: QuoteViewData): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: QuoteViewData, newItem: QuoteViewData): Boolean =
            oldItem == newItem
    }
}
