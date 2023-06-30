package com.example.e_commerce.screens.login

import androidx.lifecycle.ViewModel
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun login(
        email: String,
        password: String
    ): WrapperClass<LoginAndRegister, Boolean, Exception> {
        return repository.login(
            loginBody = mapOf(
                "email" to email,
                "password" to password
            )
        )
    }
}