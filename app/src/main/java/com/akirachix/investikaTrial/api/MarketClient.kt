package com.akirachix.investikaTrial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarketClient {
    private const val BASE_URL = "https://api.polygon.io/"

    // Create the API service
    val api: MarketApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarketApiService::class.java)
    }
}
