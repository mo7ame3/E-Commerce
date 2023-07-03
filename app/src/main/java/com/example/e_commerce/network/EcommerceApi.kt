package com.example.e_commerce.network

import com.example.e_commerce.constant.Constant
import com.example.e_commerce.model.cart.addOrDelete.AddOrDeleteCart
import com.example.e_commerce.model.cart.get.GetCart
import com.example.e_commerce.model.favorite.addOrDelete.AddOrDeleteFavorite
import com.example.e_commerce.model.favorite.get.GetFavorite
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EcommerceApi {

    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginBody: Map<String, String>
    ): LoginAndRegister


    @POST(Constant.REGISTER)
    suspend fun register(
        @Body registerBody: Map<String, String>
    ): LoginAndRegister

    @GET(Constant.HOME)
    suspend fun home(
        @Header("Authorization") authorization: String
    ): Home

    @POST(Constant.FAVORITE)
    suspend fun addFavorite(
        @Header("Authorization") authorization: String,
        @Body favoriteBody: Map<String, String>
    ): AddOrDeleteFavorite


    @POST(Constant.CART)
    suspend fun addCart(
        @Header("Authorization") authorization: String,
        @Body cartBody: Map<String, String>
    ): AddOrDeleteCart

    @GET(Constant.FAVORITE)
    suspend fun getFavorite(
        @Header("Authorization") authorization: String,
    ): GetFavorite

    @GET(Constant.CART)
    suspend fun getCart(
        @Header("Authorization") authorization: String,
    ):GetCart
}