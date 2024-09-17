package com.akirachix.investikatrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R

class housingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_housing)
        val btnContinue = findViewById<Button>(R.id.btnProceed)
        btnContinue.setOnClickListener {
            val intent = Intent (this,clothingActivity::class.java)
            startActivity(intent)
        }


    }
    }
