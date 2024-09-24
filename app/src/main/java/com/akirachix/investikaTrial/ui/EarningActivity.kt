package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityEarningBinding

class EarningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEarningBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnConfirmBuy.setOnClickListener {
            val intent = Intent(this, CongratulationsActivity::class.java)
            startActivity(intent)
        }
        binding.btnAccountOPen.setOnClickListener {
            var intent= Intent(this,CorporateActivity::class.java)
            startActivity(intent)
        }

    }
}