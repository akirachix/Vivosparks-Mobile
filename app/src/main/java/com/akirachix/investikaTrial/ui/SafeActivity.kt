package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivitySafeBinding


class SafeActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySafeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, BridgeActivity::class.java)
            startActivity(intent)
        }

    }
}