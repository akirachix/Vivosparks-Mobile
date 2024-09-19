package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityCdsBinding

class CdsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnter.setOnClickListener{
            val intent = Intent(this, CoinActivity::class.java)
            startActivity(intent)
        }
    }
}

