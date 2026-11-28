package com.example.perfumeshop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.data.PerfumeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfumeViewModel : ViewModel() {

    private val _perfumes = MutableStateFlow(PerfumeData.perfumes)
    val perfumes: StateFlow<List<Perfume>> = _perfumes.asStateFlow()

    fun getPerfumeById(id: Int): Perfume? {
        return _perfumes.value.find { it.id == id }
    }
}