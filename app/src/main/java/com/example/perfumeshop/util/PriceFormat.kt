package com.example.perfumeshop.util

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(amount: Double): String {
    val format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("ru-KZ"))
    return "${format.format(amount)} ₸"
}