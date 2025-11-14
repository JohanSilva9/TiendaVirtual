package com.example.tienda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    var quantity: Int
)