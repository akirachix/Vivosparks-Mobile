package com.example.investika

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.investika.databinding.ActivityBattleBinding
import com.example.investika.databinding.ActivityMainBinding


class BattleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, BattleQuizActivity::class.java)
            startActivity(intent)
        }
    }
}