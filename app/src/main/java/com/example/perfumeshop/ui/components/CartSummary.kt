package com.example.perfumeshop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.perfumeshop.ui.theme.PerfumeShopTheme
import com.example.perfumeshop.util.formatPrice

@Composable
fun CartSummary(
    totalPrice: Double,
    totalCount: Int,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Items: $totalCount",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total: ${formatPrice(totalPrice)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            OutlinedButton(onClick = onClear) {
                Text(text = "Clear cart")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartSummaryPreview() {
    PerfumeShopTheme {
        CartSummary(
            totalPrice = 51000.0,
            totalCount = 3,
            onClear = {}
        )
    }
}