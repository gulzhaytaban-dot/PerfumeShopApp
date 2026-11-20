package com.example.perfumeshop.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.data.PerfumeData
import com.example.perfumeshop.ui.components.PerfumeCard
import com.example.perfumeshop.ui.theme.PerfumeShopTheme
import com.example.perfumeshop.viewmodel.PerfumeViewModel

@Composable
fun HomeScreen(
    onPerfumeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PerfumeViewModel = viewModel()
) {
    val perfumes by viewModel.perfumes.collectAsState()

    HomeContent(
        perfumes = perfumes,
        onPerfumeClick = onPerfumeClick,
        modifier = modifier
    )
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
        items(perfumes, key = { it.id }) { perfume ->
            PerfumeCard(
                perfume = perfume,
                onClick = { onPerfumeClick(perfume.id) }
            )
        }
    }
}