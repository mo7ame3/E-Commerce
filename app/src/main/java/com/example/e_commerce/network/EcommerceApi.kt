package com.example.e_commerce.network

import com.example.e_commerce.constant.Constant
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import retrofit2.http.Body
import retrofit2.http.POST

interface EcommerceApi {

    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginBody: Map<String, String>
    ): LoginAndRegister

}