package com.example.investika

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.investika.databinding.ActivityBattlequizBinding
import com.example.investika.databinding.ActivityHighRiskPathsBinding
import com.example.investika.databinding.ActivityMainBinding
import com.example.investika.databinding.ActivityPathDecisionBinding


class BattleQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattlequizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBattlequizBinding.inflate(layoutInflater)
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