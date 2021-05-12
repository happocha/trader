package com.example.trader.common.extension

import androidx.fragment.app.Fragment
import com.example.trader.App

val Fragment.app get() = activity?.application as App
