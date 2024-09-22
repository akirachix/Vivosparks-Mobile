package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.AchievementAdapter
import com.akirachix.investikatrial.databinding.ActivityAchievementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding
    private lateinit var adapter: AchievementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProceed.setOnClickListener {
            var intent= Intent(this, InvestActivity::class.java)
            startActivity(intent)
        }

        binding.rvAchievements.layoutManager = LinearLayoutManager(this)

        fetchAchievements()
    }

    private fun fetchAchievements() {
        // Initialize the adapter with an empty list first
        adapter = AchievementAdapter(emptyList())
        binding.rvAchievements.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.api.achievements().execute()
            if (response.isSuccessful) {
                response.body()?.let { achievementResponse ->
                    withContext(Dispatchers.Main) {
                        // Update the adapter with the fetched achievements
                        adapter = AchievementAdapter(achievementResponse.achievements)
                        binding.rvAchievements.adapter = adapter
                    }
                }
            }
        }

    }
}
