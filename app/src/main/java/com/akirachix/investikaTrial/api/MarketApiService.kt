package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.models.MarketResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketApiService {
    @GET("v1/open-close/{symbol}/{date}")
    fun getMarketData(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("apiKey") apiKey: String
    ): Call<MarketResponse>
}
