package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityCdsBinding

class CdsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Open external URL when btnAccountOpen is clicked
        binding.btnAccountOPen.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nse.co.ke/usp/"))
            startActivity(intent)
        }

        binding.btnPLayAgain.setOnClickListener {
            val intent = Intent(this, HighRiskPaths::class.java)
            startActivity(intent)
        }
    }
}
