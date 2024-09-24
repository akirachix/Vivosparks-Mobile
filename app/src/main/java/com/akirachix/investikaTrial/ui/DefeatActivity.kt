package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityDefeatBinding

class DefeatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefeatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewBinding
        binding = ActivityDefeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the click listeners
        binding.btnTryAgain.setOnClickListener {
            val intent = Intent(this, HighRiskPaths::class.java) // Replace with your actual activity
            startActivity(intent)
        }

        binding.btncontinue.setOnClickListener {
            val intent = Intent(this, LowMidRiskActivity::class.java) // Replace with your actual activity
            startActivity(intent)
        }
    }
}
