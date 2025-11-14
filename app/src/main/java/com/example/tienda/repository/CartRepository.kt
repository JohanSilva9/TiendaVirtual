package com.example.tienda.data.repository

import com.example.tienda.data.models.CartItem
import com.example.tienda.data.room.CartDao
import kotlinx.coroutines.flow.Flow

class CartRepository(private val dao: CartDao) {
    fun getCart(): Flow<List<CartItem>> = dao.getAll()
    suspend fun addOrUpdate(item: CartItem) = dao.insert(item)
    suspend fun update(item: CartItem) = dao.update(item)
    suspend fun remove(item: CartItem) = dao.delete(item)
    suspend fun clearCart() = dao.clear()
}
