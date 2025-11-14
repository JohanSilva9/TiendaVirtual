package com.example.tienda.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.example.tienda.viewmodel.CartViewModel
import com.example.tienda.ui.components.CartItemRow

@Composable
fun CartScreen(cartViewModel: CartViewModel, onBack: () -> Unit) {
    val items by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.total.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Carrito") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Atrás") }
        }, actions = {
            TextButton(onClick = { cartViewModel.clearCart() }) { Text("Vaciar") }
        })
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Carrito vacío")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { item ->
                        CartItemRow(item = item, onRemove = { cartViewModel.remove(it) }, onQtyChange = { qty ->
                            cartViewModel.updateQuantity(item, qty)
                        })
                    }
                }
                Divider()
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = {
                        cartViewModel.checkout {
                            // al finalizar pago, queda vacío; quien llamó puede navegar
                        }
                    }) { Text("Pagar") }
                }
            }
        }
    }
}
