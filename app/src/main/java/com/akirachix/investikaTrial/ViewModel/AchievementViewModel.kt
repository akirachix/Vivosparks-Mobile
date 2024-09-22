
package com.akirachix.investikaTrial.viewmodel

import AchievementRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akirachix.investikaTrial.models.Achievement

class AchievementViewModel : ViewModel() {
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> get() = _achievements

    fun loadAchievements() {
        // Fetch achievements from the data source
        // This is a placeholder for your actual data fetching logic
        val fetchedAchievements = fetchAchievementsFromDataSource()
        if (fetchedAchievements != null) {
            _achievements.value = fetchedAchievements
            Log.d("AchievementViewModel", "Achievements loaded: $fetchedAchievements")
        } else {
            Log.e("AchievementViewModel", "Failed to load achievements")
        }
    }

    private fun fetchAchievementsFromDataSource(): List<Achievement>? {
        // Implement your data fetching logic here
        return listOf(
            Achievement(name = "First Investment", description = "Successfully made your first investment."),
            Achievement(name = "Risk Management", description = "Completed the risk management module.")
        )
    }
}
