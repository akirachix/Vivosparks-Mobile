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

        setupImageSpinner()

        binding.registerButton.setOnClickListener {
            val avatar = selectedChoice
            registerUserWithAllData(avatar)
        }
    }

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

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun registerUserWithAllData(avatar: String) {
        val intent = intent

        val registerRequest = RegisterRequest(
            username = intent.getStringExtra("username") ?: "",
            email = intent.getStringExtra("email") ?: "",
            password = intent.getStringExtra("password") ?: "",
            age = intent.getStringExtra("age")?.toIntOrNull() ?: 0,
            gender = intent.getStringExtra("gender") ?: "",
            location = intent.getStringExtra("location") ?: "",
            income = intent.getStringExtra("income") ?: "",
            avatar = avatar
        )

        // Make the API call using Retrofit
        val apiInterface = ApiClient.retrofitInstance.create(SigninInterface::class.java)
        apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ChampionActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ChampionActivity, launchgameActivity::class.java))
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@ChampionActivity, "Registration failed: $errorBody", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@ChampionActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

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
