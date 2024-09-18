package com.akirachix.investika.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investika.R
import com.akirachix.investika.databinding.ActivityRegisterProfile2Binding

class RegisterProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterProfile2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  ViewBinding
        binding = ActivityRegisterProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnprocedd.setOnClickListener {
            val intent = Intent(this, Ref::class.java)
            startActivity(intent)
        }
    }
}

