package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityChampionBinding

class ChampionActivity : AppCompatActivity() {
    lateinit var binding: ActivityChampionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityChampionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.idShadowClaw.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)

        }

        binding.idThunderWing.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)


        }

        binding.idAuroraBreath.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)

        }

        binding.idMysticFlare.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)

        }
    }
}