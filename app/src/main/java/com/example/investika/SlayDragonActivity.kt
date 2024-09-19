package com.example.investika

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.investika.databinding.ActivityBattleBinding
import com.example.investika.databinding.ActivityMainBinding
import com.example.investika.databinding.ActivitySlaydragonBinding


class SlayDragonActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySlaydragonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySlaydragonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, DefeatedActivity::class.java)
            startActivity(intent)
        }
    }
}