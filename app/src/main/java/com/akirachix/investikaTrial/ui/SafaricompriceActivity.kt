package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivitySafaricompriceBinding

class SafaricompriceActivity : AppCompatActivity() {
    // Declare the binding object for the activity layout
    private lateinit var binding: ActivitySafaricompriceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the binding object with the activity's layout inflater
        binding = ActivitySafaricompriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener for the button
       binding.btnProceedTransaction.setOnClickListener{
           val intent = Intent(this, StockpriceActivity::class.java)
           startActivity(intent)
       }
    }
}
