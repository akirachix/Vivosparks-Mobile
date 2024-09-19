package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityCongratulationsBinding
import com.akirachix.investikatrial.databinding.ActivityLivestocksBinding

class LivestocksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityLivestocksBinding
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_livestocks)

                binding = ActivityLivestocksBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.btnProceedInvest.setOnClickListener {
                    val intent = Intent(this,SafaricompriceActivity::class.java)
                    startActivity(intent)
                }
            }
        }


