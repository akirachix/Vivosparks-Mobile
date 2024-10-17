package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityCreatprofileBinding

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnprocedd.setOnClickListener {
            val age = binding.idAge.text.toString()
            val gender = if (binding.radioMale.isChecked) "male" else "female"
            val location = binding.idLocation.text.toString()
            val income = binding.idIncome.text.toString()

            // Input validation
            if (age.isEmpty() || location.isEmpty() || income.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Get data passed from RegisterActivity
                val username = intent.getStringExtra("username") ?: ""
                val email = intent.getStringExtra("email") ?: ""
                val password = intent.getStringExtra("password") ?: ""

                // Proceed to ChampionActivity with all collected data
                val intent = Intent(this, ChampionActivity::class.java).apply {
                    putExtra("username", username)
                    putExtra("email", email)
                    putExtra("password", password)
                    putExtra("age", age)
                    putExtra("gender", gender)
                    putExtra("location", location)
                    putExtra("income", income)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
