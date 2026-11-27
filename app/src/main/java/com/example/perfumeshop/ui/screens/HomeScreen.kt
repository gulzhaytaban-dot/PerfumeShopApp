package com.example.perfumeshop.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.ui.components.PerfumeCard
import com.example.perfumeshop.ui.state.PerfumeUiState
import com.example.perfumeshop.ui.theme.PerfumeShopTheme
import com.example.perfumeshop.viewmodel.PerfumeViewModel

@Composable
fun HomeScreen(
    onPerfumeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PerfumeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is PerfumeUiState.Loading -> LoadingContent(modifier)
        is PerfumeUiState.Error -> ErrorContent(
            message = state.message,
            onRetry = viewModel::retry,
            modifier = modifier
        )
        is PerfumeUiState.Empty -> EmptyContent(modifier)
        is PerfumeUiState.Success -> HomeContent(
            perfumes = state.perfumes,
            onPerfumeClick = onPerfumeClick,
            modifier = modifier
        )
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Could not load perfumes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No perfumes found",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HomeContent(
    perfumes: List<Perfume>,
    onPerfumeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Perfume Shop",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(perfumes, key = { it.id }) { perfume ->
            PerfumeCard(
                perfume = perfume,
                onClick = { onPerfumeClick(perfume.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorContentPreview() {
    PerfumeShopTheme {
        ErrorContent(message = "Unable to resolve host", onRetry = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyContentPreview() {
    PerfumeShopTheme {
        EmptyContent()
    }
}