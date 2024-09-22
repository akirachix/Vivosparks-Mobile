package com.akirachix.investikaTrial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akirachix.investikaTrial.api.ApiInterface
import com.akirachix.investikaTrial.models.QuestionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionRepository(private val api: ApiInterface) {

    suspend fun getQuestions(): LiveData<QuestionResponse> {
        val liveData = MutableLiveData<QuestionResponse>()

        val callback = object : Callback<QuestionResponse> {
            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {
                if (response.isSuccessful) {
                    val questionResponse = QuestionResponse(response.body()?.questions ?: emptyList())
                    questionResponse.setCallback { liveData.value = it }
                    questionResponse.enqueue()
                } else {
                    //  unsuccessful response
                }
            }

            override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                //  failure
            }
        }

        api.getQuestions().enqueue()

        return liveData
    }
}
