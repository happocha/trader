package com.example.trader.feature.quotes.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.trader.R

class QuotesDivideDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private var drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.bg_divider)
    private val spaceLarge = context.resources.getDimensionPixelSize(R.dimen.spacing_large)
    private val spaceSmall = context.resources.getDimensionPixelSize(R.dimen.spacing_small)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val left = parent.paddingLeft + spaceLarge
        val right = parent.width

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + spaceSmall
            val bottom = top + (drawable?.intrinsicHeight ?: 0)

            drawable?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}