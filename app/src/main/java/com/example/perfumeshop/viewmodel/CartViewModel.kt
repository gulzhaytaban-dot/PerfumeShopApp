package com.example.perfumeshop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.perfumeshop.data.CartItem
import com.example.perfumeshop.data.Perfume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun addToCart(perfume: Perfume) {
        _uiState.update { state ->
            val existing = state.items.find { it.perfume.id == perfume.id }
            val newItems = if (existing == null) {
                state.items + CartItem(perfume = perfume, quantity = 1)
            } else {
                state.items.map {
                    if (it.perfume.id == perfume.id) it.copy(quantity = it.quantity + 1) else it
                }
            }
            state.copy(items = newItems)
        }
    }
}