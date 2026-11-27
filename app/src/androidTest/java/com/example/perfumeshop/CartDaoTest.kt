package com.example.perfumeshop

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.perfumeshop.data.local.AppDatabase
import com.example.perfumeshop.data.local.CartDao
import com.example.perfumeshop.data.local.CartEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CartDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.cartDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun item(id: Int, qty: Int = 1) = CartEntity(
        perfumeId = id,
        name = "Perfume $id",
        price = 15000.0,
        notes = "musk",
        volumeMl = 50,
        imageUrl = "https://example.com/$id.jpg",
        quantity = qty
    )

    @Test
    fun insert_and_read() {
        runBlocking {
            dao.insert(item(1))
            val all = dao.getAll().first()
            assertEquals(1, all.size)
            assertEquals("Perfume 1", all[0].name)
        }
    }

    @Test
    fun update_quantity() {
        runBlocking {
            dao.insert(item(1))
            dao.updateQuantity(1, 5)
            assertEquals(5, dao.getById(1)?.quantity)
        }
    }

    @Test
    fun delete_item() {
        runBlocking {
            dao.insert(item(1))
            dao.insert(item(2))
            dao.delete(1)
            val all = dao.getAll().first()
            assertEquals(1, all.size)
            assertEquals(2, all[0].perfumeId)
        }
    }

    @Test
    fun clear_removes_everything() {
        runBlocking {
            dao.insert(item(1))
            dao.insert(item(2))
            dao.clear()
            assertTrue(dao.getAll().first().isEmpty())
        }
    }
}