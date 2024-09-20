package com.akirachix.investikaTrial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityMarketBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:
    private var currentQuestionIndex = 0
    private var score = 0
//    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }}