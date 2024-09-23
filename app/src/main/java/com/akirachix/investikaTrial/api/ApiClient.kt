package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.models.VirtualCoin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Existing MarketClient
object MarketClient {
    private const val BASE_URL = "https://api.polygon.io/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val market: MarketInterface by lazy {
        retrofit.create(MarketInterface::class.java)
    }
}

// Existing ApiClient
object ApiClient {
    private const val BASE_URL = "https://investika-fed709cc5cec.herokuapp.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val assessmentApi: AssessmentInterface = retrofit.create(AssessmentInterface::class.java)
}

// Existing RetrofitInstance
object RetrofitInstance {
    private const val BASE_URL = "https://api.polygon.io/"

    val api: PolygonApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PolygonApi::class.java)
    }
}

// New VirtualMoney API setup

object VirtualMoney {
    private const val BASE_URL = "https://investika-fed709cc5cec.herokuapp.com/" // Base URL of your API

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val virtualMoneyApi: VirtualMoneyApi by lazy {
        retrofit.create(VirtualMoneyApi::class.java)
    }
}
