package com.akirachix.investikaTrial.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.MarketClient
import com.akirachix.investikaTrial.models.MarketResponse
import com.akirachix.investikatrial.databinding.ActivityMarketBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMarketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch the market data
        fetchMarketData()

        // Proceed button click listener
        binding.marketbtn.setOnClickListener {
            // Add your navigation or button action here
        }
    }

    // Function to fetch market data from the API
    private fun fetchMarketData() {
        val apiService = MarketClient.api
        val call = apiService.getMarketData("AAPL", "2023-01-09", true, "YOUR_API_KEY")

        // Enqueue the API call and handle response
        call.enqueue(object : Callback<MarketResponse> {
            override fun onResponse(call: Call<MarketResponse>, response: Response<MarketResponse>) {
                if (response.isSuccessful) {
                    val marketData = response.body()
                    marketData?.let {
                        displayMarketData(it)
                    }
                } else {
                    Log.e("MarketActivity", "Failed to fetch data: ${response.code()}")
                    Toast.makeText(this@MarketActivity, "Failed to fetch data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MarketResponse>, t: Throwable) {
                Log.e("MarketActivity", "Error: ${t.message}", t)
                Toast.makeText(this@MarketActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to display fetched market data on the UI
    private fun displayMarketData(marketData: MarketResponse) {
        binding.moneyF.text = "Symbol: ${marketData.symbol}"
        binding.invest.text = "Open: ${marketData.open}\nClose: ${marketData.close}\nVolume: ${marketData.volume}"
    }
}
