package com.akirachix.investikaTrial.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityMoneymarketBinding

class MoneymarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoneymarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMoneymarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
