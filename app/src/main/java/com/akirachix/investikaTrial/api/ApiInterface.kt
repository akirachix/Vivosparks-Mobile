package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.models.AssessmentResponse
import com.akirachix.investikaTrial.models.Market
import com.akirachix.investikaTrial.models.OpenCloseResponse
import com.akirachix.investikaTrial.models.VirtualCoin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketInterface {
    @GET("v2/aggs/ticker/{ticker}/range/3/day/{from}/{to}")
    suspend fun getStockData(
        @Path("ticker") ticker: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Query("apiKey") apiKey: String
    ): Market
}
interface AssessmentInterface {
    @GET("api/assessments/")
    fun getAssessments(): Call<List<AssessmentResponse>>
}


interface PolygonApi {

    // Define the endpoint for fetching the open/close data
    @GET("v1/open-close/{symbol}/{date}")
    fun getOpenCloseData(
        @Path("symbol") symbol: String,
        @Path("date") date: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("apiKey") apiKey: String
    ): Call<OpenCloseResponse>
}


interface VirtualMoneyApi {
    @GET("/api/virtualmoney")
    fun getVirtualMoney(): Call<List<VirtualCoin>>  // The endpoint will return a list of VirtualCoins
}