package com.example.tienda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tienda.data.models.Product

@Composable
fun ProductItem(product: Product, onAdd: (Int) -> Unit) {
    var qty by remember { mutableStateOf(1) }
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(model = product.image, contentDescription = product.title, modifier = Modifier.size(88.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    OutlinedTextField(value = qty.toString(), onValueChange = {
                        val v = it.filter { ch -> ch.isDigit() }
                        qty = (v.toIntOrNull() ?: 1).coerceAtLeast(1)
                    }, singleLine = true, modifier = Modifier.width(80.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onAdd(qty) }) { Text("Añadir") }
                }
            }
        }
    }
}
