package com.example.perfumeshop.data

data class CartItem(
    val perfume: Perfume,
    val quantity: Int = 1
) {
    val totalPrice: Double
        get() = perfume.price * quantity
}