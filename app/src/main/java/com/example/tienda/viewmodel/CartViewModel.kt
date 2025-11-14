package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.models.CartItem
import com.example.tienda.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CartViewModel(private val repo: CartRepository) : ViewModel() {
    val cartItems = repo.getCart().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val total = repo.getCart().map { list ->
        list.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun addProduct(productId: Int, title: String, price: Double, image: String, qty: Int = 1) {
        viewModelScope.launch {
            // For simplicity: insert/replace with provided qty (overwrite if exists)
            repo.addOrUpdate(CartItem(productId, title, price, image, qty))
        }
    }

    fun updateQuantity(item: CartItem, newQty: Int) {
        viewModelScope.launch {
            if (newQty <= 0) repo.remove(item) else {
                item.quantity = newQty
                repo.update(item)
            }
        }
    }

    fun remove(item: CartItem) {
        viewModelScope.launch { repo.remove(item) }
    }

    fun clearCart() {
        viewModelScope.launch { repo.clearCart() }
    }

    fun checkout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Simula pago: limpia carrito
            repo.clearCart()
            onSuccess()
        }
    }
}
