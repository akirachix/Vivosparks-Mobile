package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityLowMidRiskBinding
import com.akirachix.investikatrial.databinding.ActivitySafetyBinding

class LowMidRiskActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLowMidRiskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLowMidRiskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener {
            val intent = Intent(this, LowRiskActivity::class.java)
            startActivity(intent)
        }

    }
}