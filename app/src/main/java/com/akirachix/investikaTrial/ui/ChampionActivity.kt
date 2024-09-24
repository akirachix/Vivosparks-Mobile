package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
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
    private lateinit var imageSpinner: Spinner
    private lateinit var selectedImageView: ImageView
    private lateinit var selectedChoice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageSpinner = binding.idShadowClaw
        selectedImageView = binding.selectedImageView

        // Setup the spinner to allow user to choose an avatar
        setupImageSpinner()

        // Handle the register button click
        binding.registerButton.setOnClickListener {
            val avatar = selectedChoice
            // Register user using all the input data including the avatar
            registerUserWithAllData(avatar)
        }

        binding.idThunderWing.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)


        }

        binding.idAuroraBreath.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)

        }

        binding.idMysticFlare.setOnClickListener {
            val intent = Intent(this,   AwardActivity::class.java)
            startActivity(intent)

        }
    }

    // Setup the image spinner for avatar selection
    private fun setupImageSpinner() {
        val imageChoices = arrayOf("ThunderWing", "ShadowClaw", "MysticFlare", "AuroraBreath")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, imageChoices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        imageSpinner.adapter = adapter

        imageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    // Register the user using all the data, including the avatar
    private fun registerUserWithAllData(avatar: String) {
        // Get intent data passed from previous activity
        val intent = intent

        // Create a request object with all user inputs
        val registerRequest = RegisterRequest(
            username = intent.getStringExtra("username") ?: "",
            email = intent.getStringExtra("email") ?: "",
            password = intent.getStringExtra("password") ?: "",
            age = intent.getStringExtra("age")?.toIntOrNull() ?: 0,
            gender = intent.getStringExtra("gender") ?: "",
            location = intent.getStringExtra("location") ?: "",
            income = intent.getStringExtra("income") ?: "",
            avatar = avatar // Pass the selected avatar choice
        )

        // Use Retrofit to make API call for registration
        val apiInterface = ApiClient.retrofit.create(SigninInterface::class.java)
        apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    // Registration is successful
                    Toast.makeText(this@ChampionActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ChampionActivity, LaunchGameActivity::class.java))
                    finish()
                } else {
                    // Handle error response from the server
                    val errorBody = response.errorBody()?.string()
                    if (response.code() == 409) { // Assuming 409 is for duplicate username/email
                        Toast.makeText(this@ChampionActivity, "Username or Email already exists. Please choose another.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@ChampionActivity, "Registration failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }
            }


            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Show error message if there was an issue with the request
                Toast.makeText(this@ChampionActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
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
