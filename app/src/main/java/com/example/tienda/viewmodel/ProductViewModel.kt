package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.StoreRepository
import com.example.tienda.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repo: StoreRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init { loadProducts() }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _products.value = repo.getProducts()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error cargando productos"
            } finally {
                _loading.value = false
            }
        }
    }
}
