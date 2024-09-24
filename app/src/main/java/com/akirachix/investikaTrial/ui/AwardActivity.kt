package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityAwardBinding

class AwardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAwardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAwardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, DragonActivity::class.java)
            startActivity(intent)

        }
    }
}