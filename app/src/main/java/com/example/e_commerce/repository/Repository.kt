package com.example.e_commerce.repository

import android.util.Log
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.favorite.AddOrDeleteFavorite
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.network.EcommerceApi
import javax.inject.Inject


class Repository @Inject constructor(private val api: EcommerceApi) {


    private val login = WrapperClass<LoginAndRegister, Boolean, Exception>()
    private val register = WrapperClass<LoginAndRegister, Boolean, Exception>()
    private val home = WrapperClass<Home, Boolean, Exception>()
    private val favorite = WrapperClass<AddOrDeleteFavorite, Boolean, Exception>()

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

    suspend fun register(registerBody: Map<String, String>): WrapperClass<LoginAndRegister, Boolean, Exception> {
        register.loading = true
        try {
            register.data = api.register(registerBody = registerBody)
            register.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "register: $e")
            register.e = e
        }
        return register
    }

    suspend fun home(authorization: String): WrapperClass<Home, Boolean, Exception> {
        home.loading = true
        try {
            home.data = api.home(authorization = authorization)
            home.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "home: $e")
            home.e = e
        }
        return home
    }


    suspend fun favorite(
        authorization: String,
        favoriteId: String
    ): WrapperClass<AddOrDeleteFavorite, Boolean, Exception> {
        favorite.loading = true
        try {
            favorite.data = api.favorite(
                authorization = authorization, favoriteBody = mapOf(
                    "product_id" to favoriteId
                )
            )
            favorite.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "favorite: $e")
            favorite.e = e
        }
        return favorite
    }

}