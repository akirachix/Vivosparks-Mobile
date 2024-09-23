package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.models.Achievement
import com.akirachix.investikatrial.databinding.ActivityLossBinding
import com.akirachix.investikatrial.databinding.ActivityMedBinding



class LossActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLossBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLossBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAccount.setOnClickListener {
            val intent = Intent(this, AchievementActivity::class.java)
            startActivity(intent)
        }

    }
}