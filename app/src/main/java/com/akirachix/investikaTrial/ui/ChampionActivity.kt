package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.RegisterRequest
import com.akirachix.investikaTrial.models.RegisterResponse
import com.akirachix.investikatrial.databinding.ActivityChampionBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChampionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChampionBinding
    private lateinit var selectedImageView: ImageView
    private lateinit var selectedChoice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedImageView = binding.selectedImageView

        // Setup the spinner to allow user to choose an avatar
        setupImageSpinner()

        // Handle the register button click
        binding.registerButton.setOnClickListener {
            val avatar = selectedChoice // Selected avatar

            // Get the data from the previous activities
            val username = intent.getStringExtra("username") ?: ""
            val email = intent.getStringExtra("email") ?: ""
            val password = intent.getStringExtra("password") ?: ""
            val age = intent.getStringExtra("age")?.toIntOrNull() ?: 0
            val gender = intent.getStringExtra("gender") ?: ""
            val location = intent.getStringExtra("location") ?: ""
            val income = intent.getStringExtra("income") ?: ""

            // Make the final registration request with all data
            val registerRequest = RegisterRequest(
                username = username,
                email = email,
                password = password,
                age = age,
                gender = gender,
                location = location,
                income = income,
                avatar = avatar // Avatar choice from spinner
            )

            // Use Retrofit to register user
            val apiInterface = ApiClient.retrofit.create(SigninInterface::class.java)
            apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        // Registration success, navigate to next activity
                        Toast.makeText(this@ChampionActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ChampionActivity, AwardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@ChampionActivity, "Registration failed: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@ChampionActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    // Setup the image spinner for avatar selection
    private fun setupImageSpinner() {
        val imageChoices = arrayOf("ShadowClaw", "ThunderWing", "MysticFlare", "AuroraBreath")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, imageChoices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.idAvatarSpinner.adapter = adapter // Assuming you have a Spinner in your layout with this ID

        binding.idAvatarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedChoice = imageChoices[position]
                val imageUrl = getImageUrl(selectedChoice)
                Picasso.get().load(imageUrl).into(selectedImageView)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    // Helper function to get image URL based on avatar choice
    private fun getImageUrl(choice: String): String {
        return when (choice) {
            "ThunderWing" -> "https://example.com/images/thunderwing.png"
            "ShadowClaw" -> "https://example.com/images/shadowclaw.png"
            "MysticFlare" -> "https://example.com/images/mysticflare.png"
            "AuroraBreath" -> "https://example.com/images/aurorabreath.png"
            else -> ""
        }
    }
}
