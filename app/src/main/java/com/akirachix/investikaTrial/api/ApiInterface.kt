package com.akirachix.investikaTrial.api

import com.akirachix.investikaTrial.models.Achievement
import com.akirachix.investikaTrial.models.AchievementResponse
import com.akirachix.investikaTrial.models.QuestionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.MarketResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("assessments/")
    suspend fun getQuestions(): QuestionResponse

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("achievements/")
    fun achievements(): Call<AchievementResponse>

}



