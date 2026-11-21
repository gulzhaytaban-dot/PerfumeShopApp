package com.example.perfumeshop.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfumeshop.data.CartItem
import com.example.perfumeshop.data.PerfumeData
import com.example.perfumeshop.ui.components.CartItemRow
import com.example.perfumeshop.ui.components.CartSummary
import com.example.perfumeshop.ui.theme.PerfumeShopTheme
import com.example.perfumeshop.viewmodel.CartUiState
import com.example.perfumeshop.viewmodel.CartViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = viewModel()
) {
    val uiState by cartViewModel.uiState.collectAsState()

    CartContent(
        uiState = uiState,
        onIncrease = cartViewModel::increaseQuantity,
        onDecrease = cartViewModel::decreaseQuantity,
        onRemove = cartViewModel::removeFromCart,
        onClear = cartViewModel::clearCart,
        modifier = modifier
    )
}

@Composable
fun CartContent(
    uiState: CartUiState,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    onRemove: (Int) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
        if (uiState.isEmpty) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.items, key = { it.perfume.id }) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = { onIncrease(item.perfume.id) },
                        onDecrease = { onDecrease(item.perfume.id) },
                        onRemove = { onRemove(item.perfume.id) }
                    )
                }
            }
            CartSummary(
                totalPrice = uiState.totalPrice,
                totalCount = uiState.totalCount,
                onClear = onClear
            )
        }
    }
}