package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityStepsBinding


class StepsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStepsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStepsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, MedActivity::class.java)
            startActivity(intent)
        }

    }
}