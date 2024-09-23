package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityBattleQuizBinding



class BattleQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattleQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBattleQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button14.setOnClickListener {
            val intent = Intent(this, SlayDragonActivity::class.java)
            startActivity(intent)
        }
        binding.button15.setOnClickListener {
            val intent = Intent(this, SlayDragonActivity::class.java)
            startActivity(intent)
        }
        binding.button16.setOnClickListener {
            val intent = Intent(this, SlayDragonActivity::class.java)
            startActivity(intent)
        }
        binding.button12.setOnClickListener {
            val intent = Intent(this, SlayDragonActivity::class.java)
            startActivity(intent)


        }
    }
}