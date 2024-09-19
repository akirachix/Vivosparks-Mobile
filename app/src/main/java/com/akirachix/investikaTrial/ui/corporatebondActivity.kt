package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import android.widget.Button

class CorporateActivity : AppCompatActivity() {
    lateinit var binding: CongratulationsActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_corporatebond)


        val btnProceedWeekly: Button = findViewById(R.id.btnProceedWeekly)
        btnProceedWeekly.setOnClickListener {
            val intent = Intent(this, EarningActivity::class.java)
            startActivity(intent)
        }
    }
}

