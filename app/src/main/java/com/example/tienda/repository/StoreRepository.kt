package com.example.tienda.repository

import com.example.tienda.data.models.Product
import com.example.tienda.data.models.UserLogin
import com.example.tienda.data.network.ApiService

class StoreRepository(private val api: ApiService) {
    suspend fun getProducts(): List<Product> = api.getProducts()
    suspend fun login(username: String, password: String) = api.login(UserLogin(username, password))
}
