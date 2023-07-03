package com.example.e_commerce.model.cart.get

data class Data(
    val cart_items: List<CartItem>,
    val sub_total: Int,
    val total: Int
)