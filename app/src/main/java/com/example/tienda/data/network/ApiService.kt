package com.example.tienda.data.network

import com.example.tienda.data.model.Product
import com.example.tienda.data.model.UserLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @POST("auth/login")
    suspend fun login(@Body body: UserLogin): Map<String, String>
}

