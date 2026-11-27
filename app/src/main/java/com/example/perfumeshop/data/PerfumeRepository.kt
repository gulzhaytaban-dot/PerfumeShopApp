package com.example.perfumeshop.data

import com.example.perfumeshop.R
import com.example.perfumeshop.data.remote.PerfumeApi
import com.example.perfumeshop.data.remote.ProductDto
import com.example.perfumeshop.data.remote.RetrofitClient

class PerfumeRepository(
    private val api: PerfumeApi = RetrofitClient.api
) {

    suspend fun getPerfumes(): List<Perfume> {
        val response = api.getFragrances()
        return response.products.map { dto -> toPerfume(dto) }
    }

    private fun toPerfume(dto: ProductDto): Perfume {
        return Perfume(
            id = dto.id,
            name = dto.title,
            price = dto.price * USD_TO_KZT,
            notes = dto.description,
            volumeMl = 0,
            imageRes = R.drawable.ic_launcher_foreground,
            imageUrl = dto.thumbnail
        )
    }

    companion object {
        private const val USD_TO_KZT = 500.0
    }
}