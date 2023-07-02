package com.example.e_commerce.model.cart

data class Product(
    val discount: Int,
    val id: Int,
    val image: String,
    val old_price: Int,
    val price: Int
)