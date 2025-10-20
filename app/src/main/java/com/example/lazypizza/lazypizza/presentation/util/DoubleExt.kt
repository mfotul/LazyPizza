package com.example.lazypizza.lazypizza.presentation.util

import android.icu.text.NumberFormat
import java.util.Locale

fun Double.toPrice(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)
}