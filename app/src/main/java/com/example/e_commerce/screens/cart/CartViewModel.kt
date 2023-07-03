package com.example.e_commerce.screens.cart

import androidx.lifecycle.ViewModel
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.cart.get.GetCart
import com.example.e_commerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    suspend fun getCart(authorization: String): WrapperClass<GetCart, Boolean, Exception> {
        return repository.getCart(authorization = authorization)
    }
}