package com.example.tienda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tienda.data.models.CartItem

@Composable
fun CartItemRow(item: CartItem, onRemove: (CartItem) -> Unit, onQtyChange: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        AsyncImage(model = item.image, contentDescription = item.title, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, style = MaterialTheme.typography.bodyMedium)
            Text("$${item.price}", style = MaterialTheme.typography.bodySmall)
        }
        OutlinedTextField(value = item.quantity.toString(), onValueChange = {
            val v = it.filter { ch -> ch.isDigit() }
            val q = (v.toIntOrNull() ?: 0)
            onQtyChange(q)
        }, modifier = Modifier.width(80.dp), singleLine = true)
        IconButton(onClick = { onRemove(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}
