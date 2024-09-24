package com.akirachix.investikaTrial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SignInClient {
    private const val BASE_URL = "https://investika-fed709cc5cec.herokuapp.com/auth/login/"

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}