package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityWisdomBinding


class WisdomActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWisdomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWisdomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, SafeActivity::class.java)
            startActivity(intent)
        }

    }
}