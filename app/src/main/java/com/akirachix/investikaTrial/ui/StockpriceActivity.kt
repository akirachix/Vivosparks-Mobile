package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivitySafaricompriceBinding
import com.akirachix.investikatrial.databinding.ActivityStockpriceBinding

class StockpriceActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStockpriceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStockpriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmStock.setOnClickListener{
            val intent = Intent(this, CdsActivity::class.java)
            startActivity(intent)
        }

        binding.btnBac.setOnClickListener {
            var intent= Intent(this,SafaricompriceActivity::class.java)
            startActivity(intent)
        }

    }
}
