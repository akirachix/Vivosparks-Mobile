package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityAchievementScreenBinding

class AchievementScreen : AppCompatActivity() {
    lateinit var binding: ActivityAchievementScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityAchievementScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener {
            var intent= Intent(this, MarketActivity::class.java)
            startActivity(intent)
        }
    }
}