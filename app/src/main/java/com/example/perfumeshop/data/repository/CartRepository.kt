package com.example.perfumeshop.data.repository

import com.example.perfumeshop.data.CartItem
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.data.local.CartDao
import com.example.perfumeshop.data.local.toCartItem
import com.example.perfumeshop.data.local.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(private val dao: CartDao) {

    val items: Flow<List<CartItem>> =
        dao.getAll().map { list -> list.map { it.toCartItem() } }

    suspend fun add(perfume: Perfume) {
        val existing = dao.getById(perfume.id)
        if (existing == null) {
            dao.insert(perfume.toEntity(quantity = 1))
        } else {
            dao.updateQuantity(perfume.id, existing.quantity + 1)
        }
    }

    suspend fun remove(perfumeId: Int) = dao.delete(perfumeId)

    suspend fun clear() = dao.clear()
}