package com.example.trader.common.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.bumptech.glide.request.transition.TransitionFactory
import com.example.trader.R

private val crossFadeTransitionFactory = TransitionFactory { _, _ ->
    DrawableCrossFadeTransition(300, true)
}

private fun ImageView.setImage(
    url: String?,
    requestOptions: RequestOptions,
    @DrawableRes placeholderRes: Int
) {
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.with(crossFadeTransitionFactory))
        .into(this)
}

fun ImageView.setSquareImage(
    url: String?,
    @DrawableRes backgroundImage: Int = R.drawable.bg_rect_placeholder
) {
    setImage(
        url,
        RequestOptions.centerInsideTransform(),
        backgroundImage
    )
}