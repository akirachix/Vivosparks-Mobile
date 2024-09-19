package com.akirachix.investikaTrial.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.akirachix.investikatrial.databinding.ActivityCoinBinding

class CoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnopen.setOnClickListener{
            val intent = Intent(this, luckyActivity::class.java)
            startActivity(intent)
        }
    }
}
