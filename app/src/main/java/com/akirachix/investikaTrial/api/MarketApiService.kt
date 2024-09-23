package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.models.MarketResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketApiService {
    @GET("v1/open-close/{symbol}/{date}")
    fun getOpenCloseData(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("apiKey") apiKey: String
    ): Call<MarketResponse>

    @GET("v1/market-data/{symbol}")
    fun displayStockData(
        @Path("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("apiKey") apiKey: String
    ): Call<MarketResponse>

    @GET("v1/market-data/{symbol}")  // Replace with the correct endpoint for your market data
    fun getMarketData(
        @Path("symbol") symbol: String,
        @Query("date") date: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("apiKey") apiKey: String
    ): Call<MarketResponse>  // Correct return type
}
