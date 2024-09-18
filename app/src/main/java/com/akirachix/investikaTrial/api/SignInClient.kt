package com.akirachix.investikaTrial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SignInClient {
    private const val BASE_URL = "https://your-api-base-url.com/"

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
