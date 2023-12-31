package com.example.e_commerce.screens.home

import androidx.lifecycle.ViewModel
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.cart.addOrDelete.AddOrDeleteCart
import com.example.e_commerce.model.favorite.addOrDelete.AddOrDeleteFavorite
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun home(authorization: String): WrapperClass<Home, Boolean, Exception> {
        return repository.home(authorization = authorization)
    }

    suspend fun favorites(
        authorization: String,
        productId: String
    ): WrapperClass<AddOrDeleteFavorite, Boolean, Exception> {
        return repository.addFavorite(authorization = authorization, productId = productId)
    }

    suspend fun cart(
        authorization: String,
        productId: String
    ): WrapperClass<AddOrDeleteCart, Boolean, Exception> {
        return repository.addCart(authorization = authorization, productId = productId)
    }

}