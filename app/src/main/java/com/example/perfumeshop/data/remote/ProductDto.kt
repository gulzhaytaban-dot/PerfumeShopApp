package com.example.perfumeshop.data.remote

data class ProductsResponse(
    val products: List<ProductDto>,
    val total: Int
)

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val brand: String?,
    val thumbnail: String
)