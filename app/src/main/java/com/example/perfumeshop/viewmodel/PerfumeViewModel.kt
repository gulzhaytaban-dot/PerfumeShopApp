package com.example.perfumeshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.data.PerfumeRepository // TODO: fix the import if Part 1 put it in another package
import com.example.perfumeshop.ui.state.PerfumeUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfumeViewModel(
    private val repository: PerfumeRepository = PerfumeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PerfumeUiState>(PerfumeUiState.Loading)
    val uiState: StateFlow<PerfumeUiState> = _uiState.asStateFlow()

    init {
        loadPerfumes()
    }

    fun retry() {
        _uiState.value = PerfumeUiState.Loading
        loadPerfumes()
    }

    private fun loadPerfumes() {
        viewModelScope.launch {
            _uiState.value = PerfumeUiState.Loading
            try {
                val perfumes = repository.getPerfumes()
                _uiState.value = if (perfumes.isEmpty()) {
                    PerfumeUiState.Empty
                } else {
                    PerfumeUiState.Success(perfumes)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = PerfumeUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun getPerfumeById(id: Int): Perfume? {
        val state = _uiState.value
        return if (state is PerfumeUiState.Success) {
            state.perfumes.find { it.id == id }
        } else {
            null
        }
    }
}