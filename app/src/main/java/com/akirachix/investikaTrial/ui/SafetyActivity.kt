package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivitySafetyBinding

class SafetyActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySafetyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySafetyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, SageActivity::class.java)
            startActivity(intent)
        }

    }
}