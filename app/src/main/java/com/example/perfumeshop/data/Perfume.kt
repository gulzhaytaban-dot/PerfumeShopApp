package com.example.perfumeshop.data

data class Perfume(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val notes: String,
    val volumeMl: Int
)