package com.example.trader.common.extension

import android.graphics.drawable.Drawable

val Drawable.isNotEmpty get() = minimumWidth > 1 && minimumHeight > 1
