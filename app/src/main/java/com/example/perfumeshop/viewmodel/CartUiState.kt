package com.example.perfumeshop.viewmodel

import com.example.perfumeshop.data.CartItem

data class CartUiState(
    val items: List<CartItem> = emptyList()
) {
    val totalPrice: Double
        get() = items.sumOf { it.totalPrice }

    val totalCount: Int
        get() = items.sumOf { it.quantity }

    val isEmpty: Boolean
        get() = items.isEmpty()
}