package com.example.trader.common

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class SpaceDecoration(
    @Px private val spacingLeft: Int = 0,
    @Px private val spacingTop: Int = 0,
    @Px private val spacingRight: Int = 0,
    @Px private val spacingBottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(spacingLeft, spacingTop, spacingRight, spacingBottom)
    }
}