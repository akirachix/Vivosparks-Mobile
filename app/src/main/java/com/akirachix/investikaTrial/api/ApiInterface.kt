package com.akirachix.investikaTrial.api


import com.akirachix.investikaTrial.models.QuestionResponse
import retrofit2.http.GET

interface ApiInterface {
    @GET("assessments/")
    suspend fun getQuestions(): QuestionResponse
}

