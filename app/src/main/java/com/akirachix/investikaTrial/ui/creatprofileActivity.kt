package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityCreatprofileBinding
import com.akirachix.investikatrial.databinding.ActivityRegisterBinding

class creatprofileActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreatprofileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launchgame)

        binding = ActivityCreatprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnprocedd.setOnClickListener {
            val intent = Intent(this, championActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}