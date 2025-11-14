package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.CartRepository
import com.example.tienda.data.prefs.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: StoreRepository,
    private val store: DataStoreManager
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val res = repo.login(username, password)
                val token = res["token"]
                if (!token.isNullOrEmpty()) {
                    store.setLoggedIn(true)
                    onSuccess()
                } else {
                    _error.value = "Credenciales inválidas"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error en login"
            } finally {
                _loading.value = false
            }
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            store.setLoggedIn(false)
            onDone()
        }
    }
}
