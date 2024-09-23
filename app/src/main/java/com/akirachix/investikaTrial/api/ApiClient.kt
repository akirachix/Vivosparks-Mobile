package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.api.ApiClient.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Existing MarketClient

// Existing ApiClient
object ApiClient {
    val api: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
    private const val BASE_URL = "https://investika-fed709cc5cec.herokuapp.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val assessmentApi: AssessmentInterface = retrofit.create(AssessmentInterface::class.java)



}

private const val BASE_URL = "https://investika-fed709cc5cec.herokuapp.com/"
private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val api: ApiInterface by lazy {
    retrofit.create(ApiInterface::class.java)
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
