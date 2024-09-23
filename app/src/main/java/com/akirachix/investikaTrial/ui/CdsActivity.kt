package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.models.AssessmentResponse
import com.akirachix.investikatrial.databinding.ActivityAssessmentBinding
import com.akirachix.investikatrial.databinding.ActivityCdsBinding

class CdsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAccountOPen.setOnClickListener {
            val intent = Intent(this, AssessmentActivity::class.java)
            startActivity(intent)
        }

    }
}

