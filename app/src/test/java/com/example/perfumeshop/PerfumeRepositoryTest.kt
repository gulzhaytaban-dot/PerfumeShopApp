package com.example.perfumeshop

import com.example.perfumeshop.data.PerfumeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class PerfumeRepositoryTest {

    @Test
    fun getPerfumes_returnsDataFromApi() = runBlocking {
        val perfumes = PerfumeRepository().getPerfumes()

        println(perfumes.map { it.name })

        assertTrue(perfumes.isNotEmpty())
        assertTrue(perfumes.all { it.imageUrl != null })
    }
}