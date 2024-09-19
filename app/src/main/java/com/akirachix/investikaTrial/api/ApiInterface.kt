package com.akirachix.investikaTrial.api


import com.akirachix.investikaTrial.models.QuestionResponse
import retrofit2.http.GET
import com.akirachix.investikaTrial.models.Question
import retrofit2.Response

interface ApiInterface {
    @GET("assessments/")
    suspend fun getQuestions(): QuestionResponse
}

