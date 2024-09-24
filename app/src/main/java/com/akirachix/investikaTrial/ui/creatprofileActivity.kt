//package com.akirachix.investikaTrial.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.akirachix.investikatrial.databinding.ActivityCreatprofileBinding
//
//class createprofileactivity : AppCompatActivity() {
//    private lateinit var binding: ActivityCreatprofileBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCreatprofileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnprocedd.setOnClickListener {
//            val age = binding.ageTextInput.text.toString()
//            val gender = if (binding.radioMale.isChecked) "male" else "female"
//            val location = binding.locationInput.text.toString()
//            val income = binding.income.text.toString()
//
//            if (age.isNotEmpty() && location.isNotEmpty() && income.isNotEmpty()) {
//                // Get data from RegisterActivity
//                val username = intent.getStringExtra("username") ?: ""
//                val email = intent.getStringExtra("email") ?: ""
//                val password = intent.getStringExtra("password") ?: ""
//
//                // Pass all data to ChampionActivity
//                val intent = Intent(this, ChampionActivity::class.java).apply {
//                    putExtra("username", username)
//                    putExtra("email", email)
//                    putExtra("password", password)
//                    putExtra("age", age)
//                    putExtra("gender", gender)
//                    putExtra("location", location)
//                    putExtra("income", income)
//                }
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}