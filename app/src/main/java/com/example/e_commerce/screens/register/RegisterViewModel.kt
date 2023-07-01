package com.example.e_commerce.screens.register

import androidx.lifecycle.ViewModel
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun register(
        email: String,
        name: String,
        phone: String,
        password: String
    ): WrapperClass<LoginAndRegister, Boolean, Exception> {
        return repository.register(
            registerBody = mapOf(
                "email" to email,
                "password" to password,
                "name" to name,
                "phone" to phone,
            )
        )
    }
}