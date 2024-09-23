package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akirachix.investikaTrial.api.MarketClient
import com.akirachix.investikaTrial.models.Market
import com.akirachix.investikaTrial.models.MarketResponse
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityCorporatebondBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class CorporateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCorporatebondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorporatebondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnProceedWeekly: Button = findViewById(R.id.btnProceedWeekly)
        btnProceedWeekly.setOnClickListener {
            val intent = Intent(this, EarningActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            fetchMarketData()
        }
    }

    private fun fetchMarketData() {
        val apiService = MarketClient.api
        val call = apiService.getMarketData("AAPL", "2023-01-09", true, "A4Rwrkjq0CkkXQ30xuKhntrYerv0CgUs")

        // Enqueue the API call and handle the response
        call.enqueue(object : Callback<MarketResponse> {
            override fun onResponse(call: Call<MarketResponse>, response: Response<MarketResponse>) {
                if (response.isSuccessful) {
                    val marketData = response.body()
                    marketData?.let {
                        displayStockData()
                    }
                } else {
                    Log.e("CorporateActivity", "Failed to fetch data: ${response.code()}")
                    Toast.makeText(this@CorporateActivity, "Failed to fetch data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MarketResponse>, t: Throwable) {
                Log.e("CorporateActivity", "Error: ${t.message}", t)
                Toast.makeText(this@CorporateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayStockData() {
        TODO("Not yet implemented")
    }

    private fun displayStockData(result: Market) {
        Log.d("CorporateActivity", "Open: ${result.o}, Close: ${result.c}, High: ${result.h}, Low: ${result.l}, Volume: ${result.v}")

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
