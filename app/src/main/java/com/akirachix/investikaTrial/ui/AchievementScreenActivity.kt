package com.akirachix.investikaTrial.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityAchievementScreenBinding

class AchievementScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityAchievementScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAchievementScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}