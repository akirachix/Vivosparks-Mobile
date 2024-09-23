package com.akirachix.investikaTrial.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.RegisterRequest
import com.akirachix.investikaTrial.models.RegisterResponse

interface SigninInterface {
    @POST("/auth/login/") // Replace with your actual endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register/")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}

