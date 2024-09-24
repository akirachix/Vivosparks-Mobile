package com.akirachix.investikaTrial.api


import retrofit2.http.Body
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest
import retrofit2.Call

import retrofit2.Response

interface SigninInterface {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
