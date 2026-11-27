package com.example.perfumeshop.data.remote

import retrofit2.http.GET

interface PerfumeApi {

    @GET("products/category/fragrances")
    suspend fun getFragrances(): ProductsResponse
}