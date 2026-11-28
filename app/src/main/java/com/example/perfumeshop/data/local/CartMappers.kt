package com.example.perfumeshop.data.local

import com.example.perfumeshop.data.CartItem
import com.example.perfumeshop.data.Perfume

fun CartEntity.toCartItem(): CartItem = CartItem(
    perfume = Perfume(
        id = perfumeId,
        name = name,
        price = price,
        notes = notes,
        volumeMl = volumeMl,
        imageRes = 0,
        imageUrl = imageUrl
    ),
    quantity = quantity
)

fun Perfume.toEntity(quantity: Int): CartEntity = CartEntity(
    perfumeId = id,
    name = name,
    price = price,
    notes = notes,
    volumeMl = volumeMl,
    imageUrl = imageUrl,
    quantity = quantity
)