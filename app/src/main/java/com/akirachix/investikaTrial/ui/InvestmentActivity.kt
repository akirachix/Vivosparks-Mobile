package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityAchievementBinding
import com.akirachix.investikatrial.databinding.ActivityInvestBinding
import com.akirachix.investikatrial.databinding.ActivityInvestmentBinding

class InvestmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityInvestmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInvestmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            var intent= Intent(this,AchievementScreen::class.java )
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            var intent= Intent(this,AchievementScreen::class.java )
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            var intent= Intent(this,AchievementScreen::class.java )
            startActivity(intent)
        }


    }
}