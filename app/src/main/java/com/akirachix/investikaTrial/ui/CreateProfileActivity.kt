package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityCreatprofileBinding

class CreateProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreatprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCreatprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnprocedd.setOnClickListener {
            val intent = Intent(this, ChampionActivity::class.java)
            startActivity(intent)

        }
    }
}