package com.example.perfumeshop.data

import androidx.annotation.DrawableRes
import java.text.NumberFormat
import java.util.Locale

data class Perfume(
    val id: Int,
    val name: String,
    val price: Double,
    val notes: String,
    val volumeMl: Int,
    @DrawableRes val imageRes: Int,
    val imageUrl: String? = null
) {
    fun formattedPrice(): String {
        val format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("ru-KZ"))
        return "${format.format(price)} ₸"
    }
}