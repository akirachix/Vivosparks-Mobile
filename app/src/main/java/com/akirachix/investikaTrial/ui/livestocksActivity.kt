package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.RetrofitInstance
import com.akirachix.investikaTrial.models.OpenCloseResponse
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityLivestocksBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LivestocksActivity : AppCompatActivity() {

    // Declare TextView variables
    private lateinit var symbolTextView: TextView
    private lateinit var openTextView: TextView
    private lateinit var closeTextView: TextView
    private lateinit var highTextView: TextView
    private lateinit var lowTextView: TextView
    private lateinit var volumeTextView: TextView
    private lateinit var afterHoursTextView: TextView
    private lateinit var preMarketTextView: TextView

    private lateinit var binding: ActivityLivestocksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityLivestocksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find views by ID
        symbolTextView = findViewById(R.id.symbol)
        openTextView = findViewById(R.id.open)
        closeTextView = findViewById(R.id.close)
        highTextView = findViewById(R.id.high)
        lowTextView = findViewById(R.id.low)
        volumeTextView = findViewById(R.id.volume)
        afterHoursTextView = findViewById(R.id.after_hours)
        preMarketTextView = findViewById(R.id.pre_market)

        // Fetch the stock data
        fetchOpenCloseData("AAPL", "2024-07-23")

        // Set up button click listener for proceeding to another activity
        binding.btnProceedInvest.setOnClickListener {
            val intent = Intent(this, SafaricompriceActivity::class.java)
            startActivity(intent)
        }
    }

    // Fetch open and close data from API
    private fun fetchOpenCloseData(symbol: String, date: String) {
        val apiKey = "A4Rwrkjq0CkkXQ30xuKhntrYerv0CgUs"
        RetrofitInstance.api.getOpenCloseData(symbol, date, true, apiKey)
            .enqueue(object : Callback<OpenCloseResponse> {
                override fun onResponse(
                    call: Call<OpenCloseResponse>,
                    response: Response<OpenCloseResponse>
                ) {
                    if (response.isSuccessful) {
                        val openCloseData = response.body()
                        openCloseData?.let {
                            // Display the data in TextViews
                            symbolTextView.text = it.symbol
                            openTextView.text = "Open: ${it.open}"
                            closeTextView.text = "Close: ${it.close}"
                            highTextView.text = "High: ${it.high}"
                            lowTextView.text = "Low: ${it.low}"
                            volumeTextView.text = "Volume: ${it.volume}"

                            // Display after-hours and pre-market if available
                            afterHoursTextView.text = "After Hours: ${it.afterHours ?: "N/A"}"
                            preMarketTextView.text = "Pre Market: ${it.preMarket ?: "N/A"}"
                        }
                    } else {
                        Toast.makeText(
                            this@LivestocksActivity,
                            "Failed to fetch data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<OpenCloseResponse>, t: Throwable) {
                    Toast.makeText(this@LivestocksActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
