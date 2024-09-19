package com.example.investika

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.investika.databinding.ActivityDefeatedBinding
import com.example.investika.databinding.ActivityMainBinding


class DefeatedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefeatedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDefeatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
