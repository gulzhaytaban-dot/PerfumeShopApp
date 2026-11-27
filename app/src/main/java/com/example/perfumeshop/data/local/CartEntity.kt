package com.example.perfumeshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val perfumeId: Int,
    val name: String,
    val price: Double,
    val notes: String,
    val volumeMl: Int,
    val imageUrl: String,
    val quantity: Int
)