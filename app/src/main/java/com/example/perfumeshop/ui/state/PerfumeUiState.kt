package com.example.perfumeshop.ui.state

import com.example.perfumeshop.data.Perfume

sealed interface PerfumeUiState {
    object Loading : PerfumeUiState
    data class Success(val perfumes: List<Perfume>) : PerfumeUiState
    data class Error(val message: String) : PerfumeUiState
    object Empty : PerfumeUiState
}
