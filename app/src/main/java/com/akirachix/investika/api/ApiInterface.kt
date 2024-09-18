package com.akirachix.investika.api
import com.akirachix.investika.model.RegisterRequest
import com.akirachix.investika.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("register/")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}
