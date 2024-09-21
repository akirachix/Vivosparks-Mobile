package com.akirachix.investikaTrial.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest

interface ApiInterface {
    @POST("/auth/login/") // Replace with your actual endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}


