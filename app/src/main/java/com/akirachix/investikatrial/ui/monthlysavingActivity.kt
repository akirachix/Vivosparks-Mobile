package com.akirachix.investikatrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R

class monthlysavingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_monthlysaving)
        val btnContinue = findViewById<Button>(R.id.btnProceed)
        btnContinue.setOnClickListener {
            val intent = Intent (this,awardActivity::class.java)
            startActivity(intent)
        }
    }
}