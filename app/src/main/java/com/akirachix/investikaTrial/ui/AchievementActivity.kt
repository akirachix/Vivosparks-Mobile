package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.Achievement
import com.akirachix.investikatrial.databinding.ActivityAchievementBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch achievements when the activity is created
        fetchAchievements()

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, InvestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAchievements() {
        val call = ApiClient.api.achievements() // No token needed

        call.enqueue(object : Callback<List<Achievement>> {
            override fun onResponse(call: Call<List<Achievement>>, response: Response<List<Achievement>>) {
                if (response.isSuccessful) {
                    val achievements = response.body()
                    achievements?.let {
                        displayAchievements(it) // Display achievements
                    }
                } else {
                    Log.e("Error", "Failed to fetch achievements: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Achievement>>, t: Throwable) {
                Log.e("Error", "API call failed: ${t.message}")
            }
        })
    }

    private fun displayAchievements(achievements: List<Achievement>) {
        val achievementsText = achievements.joinToString("\n") {
            "${it.name}: ${it.description}"
        }

    }
}
