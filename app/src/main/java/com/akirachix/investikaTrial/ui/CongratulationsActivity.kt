package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityCongratulationsBinding

class CongratulationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCongratulationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCongratulationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToStock.setOnClickListener {
            val intent = Intent(this, LivestocksActivity::class.java)
            startActivity(intent)
        }
    }
}
