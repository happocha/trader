package com.example.trader.common.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

inline fun <reified T> ViewGroup.inflate(@LayoutRes layout: Int) =
    LayoutInflater.from(context).inflate(layout, this, false) as T