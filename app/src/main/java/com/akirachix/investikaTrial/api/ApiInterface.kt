package com.akirachix.investikaTrial.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest

interface ApiInterface {
    @POST("login_endpoint") // Replace with your actual endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}


