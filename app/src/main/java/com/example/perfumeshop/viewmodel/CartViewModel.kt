package com.example.perfumeshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val cartWriteMutex = Mutex()

    val uiState: StateFlow<CartUiState> = repository.items
        .map { items -> CartUiState(items = items) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState()
        )

    fun addToCart(perfume: Perfume) {
        updateCart { repository.add(perfume) }
    }

    fun increaseQuantity(perfumeId: Int) {
        updateCart { repository.increase(perfumeId) }
    }

    fun decreaseQuantity(perfumeId: Int) {
        updateCart { repository.decrease(perfumeId) }
    }

    fun removeFromCart(perfumeId: Int) {
        updateCart { repository.remove(perfumeId) }
    }

    fun clearCart() {
        updateCart { repository.clear() }
    }

    private fun updateCart(action: suspend () -> Unit) {
        viewModelScope.launch {
            cartWriteMutex.withLock { action() }
        }
    }

    companion object {
        fun factory(repository: CartRepository) = viewModelFactory {
            initializer { CartViewModel(repository) }
        }
    }
}