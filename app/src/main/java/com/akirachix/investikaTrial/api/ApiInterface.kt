package com.akirachix.investikaTrial.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.UserResponse

interface ApiInterface {
    @POST("/login/") // Replace with your actual endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/users/") // Replace with your actual endpoint for fetching users
    fun getUsers(): Call<List<UserResponse>>
}



