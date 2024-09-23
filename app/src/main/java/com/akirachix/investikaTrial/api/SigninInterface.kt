package com.akirachix.investikaTrial.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.RegisterRequest
import com.akirachix.investikaTrial.models.RegisterResponse
import retrofit2.Response

interface SigninInterface {
    @POST("/auth/login/")
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>


    @POST(/* value = */ "/api/register/")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>



}

