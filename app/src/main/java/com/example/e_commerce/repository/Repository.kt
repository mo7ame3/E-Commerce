package com.example.e_commerce.repository

import android.util.Log
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.cart.addOrDelete.AddOrDeleteCart
import com.example.e_commerce.model.cart.get.GetCart
import com.example.e_commerce.model.favorite.addOrDelete.AddOrDeleteFavorite
import com.example.e_commerce.model.favorite.get.GetFavorite
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.network.EcommerceApi
import javax.inject.Inject


class Repository @Inject constructor(private val api: EcommerceApi) {


    private val login = WrapperClass<LoginAndRegister, Boolean, Exception>()
    private val register = WrapperClass<LoginAndRegister, Boolean, Exception>()
    private val home = WrapperClass<Home, Boolean, Exception>()
    private val addFavorite = WrapperClass<AddOrDeleteFavorite, Boolean, Exception>()
    private val getFavorite = WrapperClass<GetFavorite, Boolean, Exception>()
    private val addCart = WrapperClass<AddOrDeleteCart, Boolean, Exception>()
    private val getCart = WrapperClass<GetCart, Boolean, Exception>()

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


    suspend fun addFavorite(
        authorization: String,
        productId: String
    ): WrapperClass<AddOrDeleteFavorite, Boolean, Exception> {
        addFavorite.loading = true
        try {
            addFavorite.data = api.addFavorite(
                authorization = authorization, favoriteBody = mapOf(
                    "product_id" to productId
                )
            )
            addFavorite.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "addFavorite: $e")
            addFavorite.e = e
        }
        return addFavorite
    }

    suspend fun getFavorite(
        authorization: String,
    ): WrapperClass<GetFavorite, Boolean, Exception> {
        getFavorite.loading = true
        try {
            getFavorite.data = api.getFavorite(
                authorization = authorization,
            )
            getFavorite.loading = false
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getFavorite: $e")
            getFavorite.e = e
        }
        return getFavorite
    }

    suspend fun addCart(
        authorization: String,
        productId: String
    ): WrapperClass<AddOrDeleteCart, Boolean, Exception> {
        addCart.loading = true
        try {
            addCart.data = api.addCart(
                authorization = authorization, cartBody = mapOf(
                    "product_id" to productId
                )
            )
            addCart.loading = false
        } catch (e: Exception) {
            Log.d("TAG", "addCart: $e")
            addCart.e = e
        }
        return addCart
    }

    suspend fun getCart(
        authorization: String,
    ): WrapperClass<GetCart, Boolean, Exception> {
        getCart.loading = true
        try {
            getCart.data = api.getCart(
                authorization = authorization
            )
            getCart.loading = false
        } catch (e: Exception) {
            Log.d("TAG", "getCart: $e")
            getCart.e = e
        }
        return getCart
    }

}