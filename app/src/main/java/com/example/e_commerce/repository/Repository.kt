package com.example.e_commerce.repository

import android.util.Log
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.network.EcommerceApi
import javax.inject.Inject


class Repository @Inject constructor(private val api: EcommerceApi) {


    private val login = WrapperClass<LoginAndRegister, Boolean, Exception>()

    suspend fun login(loginBody: Map<String, String>): WrapperClass<LoginAndRegister, Boolean, Exception> {
        login.loading = true
        try {
            login.data = api.login(loginBody = loginBody)
            login.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "login: $e")
            login.e = e
        }
        return login
    }

}