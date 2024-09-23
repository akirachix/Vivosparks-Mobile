package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivitySageBinding

class SageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button1.setOnClickListener {
            val intent = Intent(this, WisdomActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            val intent = Intent(this, WisdomActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this, WisdomActivity::class.java)
            startActivity(intent)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(this, WisdomActivity::class.java)
            startActivity(intent)
        }

    }
}