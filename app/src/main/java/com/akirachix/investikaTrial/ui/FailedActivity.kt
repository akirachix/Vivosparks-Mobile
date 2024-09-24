package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityFailedBinding

class FailedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button to retry, navigating to HighRiskPathActivity
        binding.btnTryagain.setOnClickListener {
            val intent = Intent(this, HighRiskPaths::class.java)
            startActivity(intent)
        }

        // Button to continue, navigating to LowRiskActivity

        }
    
}
