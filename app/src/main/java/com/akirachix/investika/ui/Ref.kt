package com.akirachix.investika.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investika.R
import com.akirachix.investika.databinding.ActivityRefBinding
import com.akirachix.investika.databinding.ActivityRegisterProfile2Binding

class Ref : AppCompatActivity() {
    private lateinit var binding: ActivityRefBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  ViewBinding
        binding = ActivityRefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnprocedd.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
    }
}

