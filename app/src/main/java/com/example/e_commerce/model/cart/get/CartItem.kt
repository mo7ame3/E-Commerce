package com.example.e_commerce.model.cart.get

data class CartItem(
    val id: Int,
    val product: Product,
    val quantity: Int
)