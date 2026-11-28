package com.example.perfumeshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntity)

    @Query("SELECT * FROM cart_items ORDER BY perfumeId")
    fun getAll(): Flow<List<CartEntity>>

    @Query("UPDATE cart_items SET quantity = :quantity WHERE perfumeId = :perfumeId")
    suspend fun updateQuantity(perfumeId: Int, quantity: Int)

    @Query("DELETE FROM cart_items WHERE perfumeId = :perfumeId")
    suspend fun delete(perfumeId: Int)

    @Query("SELECT * FROM cart_items WHERE perfumeId = :perfumeId LIMIT 1")
    suspend fun getById(perfumeId: Int): CartEntity?

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}