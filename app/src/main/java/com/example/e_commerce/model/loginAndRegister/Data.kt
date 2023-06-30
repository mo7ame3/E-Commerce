package com.example.e_commerce.model.loginAndRegister

data class Data(
    val credit: Int? = null,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val points: Int? = null,
    val token: String
)