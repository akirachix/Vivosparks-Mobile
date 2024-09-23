package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikatrial.databinding.ActivityAchievementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, InvestActivity::class.java)
            startActivity(intent)
        }
        

        fetchAchievements()
    }

    private fun fetchAchievements() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.api.achievements().execute()
            if (response.isSuccessful) {
                response.body()?.let { achievementResponse ->
                    withContext(Dispatchers.Main) {
                        // Do something with the fetched achievements
                        // For example, display them in the UI without using an adapter
                        binding.tvAchievements.text = achievementResponse.achievements.joinToString("\n") { achievement ->
                            achievement.name  // Assuming your achievements have a name field
                        }
                    }
                }
            }
        }
    }
}
