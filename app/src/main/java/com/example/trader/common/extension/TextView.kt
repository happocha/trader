package com.example.trader.common.extension

import android.widget.TextView

fun TextView.showTextOrHide(str: CharSequence?) {
    this.text = str
    this.showNow(str.isNullOrBlank().not())
}

