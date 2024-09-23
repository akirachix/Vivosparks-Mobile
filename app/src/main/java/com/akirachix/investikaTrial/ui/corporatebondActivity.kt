package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.akirachix.investikaTrial.api.MarketClient
import com.akirachix.investikaTrial.models.Market
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityCorporatebondBinding
import kotlinx.coroutines.launch
import android.util.Log

class CorporateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCorporatebondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCorporatebondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnProceedWeekly: Button = findViewById(R.id.btnProceedWeekly)
        btnProceedWeekly.setOnClickListener {
            val intent = Intent(this, EarningActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            fetchStockData("AAPL", "2024-07-09", "2024-08-09")
        }
    }

    private suspend fun fetchStockData(ticker: String, from: String, to: String) {
        try {
            var result: Market? = null
            var attempts = 0
            val maxAttempts = 5

            while (result == null && attempts < maxAttempts) {
                result = MarketClient.market.getStockData(ticker, from, to, "A4Rwrkjq0CkkXQ30xuKhntrYerv0CgUs")
                if (result != null && !result.isEmpty()) {
                    displayStockData(result)
                    break
                }
                attempts++
                if (attempts < maxAttempts) {
                    kotlinx.coroutines.delay(1000)
                }
            }

            if (result == null || result.isEmpty()) {
                Toast.makeText(this@CorporateActivity, "Failed to fetch valid data after $maxAttempts attempts", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this@CorporateActivity, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("CorporateActivity", "Error fetching stock data", e)
        }
    }

    private fun displayStockData(result: Market) {
        Log.d("Market Data", "Open: ${result.o}, Close: ${result.c}, High: ${result.h}, Low: ${result.l}, Volume: ${result.v}")

        binding.tvOpen.text = "Open: ${result.o ?: "N/A"}"
        binding.tvClose.text = "Close: ${result.c ?: "N/A"}"
        binding.tvHigh.text = "High: ${result.h ?: "N/A"}"
        binding.tvLow.text = "Low: ${result.l ?: "N/A"}"
        binding.tvVolume.text = "Volume: ${result.v ?: "N/A"}"
    }

    private fun Market.isEmpty(): Boolean {
        return o == null && c == null && h == null && l == null && v == null
    }
}