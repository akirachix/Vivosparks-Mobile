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

class InvestActivity : AppCompatActivity() {
    lateinit var binding: ActivityInvestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInvestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProceed.setOnClickListener {
            var intent= Intent(this,InvestmentActivity::class.java )
            startActivity(intent)
        }

        }

}