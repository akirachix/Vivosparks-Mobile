
package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityMarketBinding

class MarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.marketbtn.setOnClickListener {
            var intent= Intent(this,MoneymarketActivity::class.java)
            startActivity(intent)
        }

    }
}
