package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityAwardBinding
import com.akirachix.investikatrial.databinding.ActivityChampionBinding

class awardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAwardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launchgame)

        binding = ActivityAwardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, masterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}