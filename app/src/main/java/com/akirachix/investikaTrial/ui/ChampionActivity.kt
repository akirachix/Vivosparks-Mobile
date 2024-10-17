package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.RegisterRequest
import com.akirachix.investikaTrial.models.RegisterResponse
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityChampionBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class ChampionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChampionBinding
    private lateinit var selectedChoice: String
    private var isAvatarSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for avatar images
        setupAvatarSelection()
    }

    private fun setupAvatarSelection() {
        binding.Imgshadow.setOnClickListener { selectAvatar("ShadowClaw", binding.Imgshadow) }
        binding.imageView3.setOnClickListener { selectAvatar("ThunderWing", binding.imageView3) }
        binding.imageView4.setOnClickListener { selectAvatar("MysticFlare", binding.imageView4) }
        binding.imageView5.setOnClickListener { selectAvatar("AuroraBreath", binding.imageView5) }
    }

    private fun selectAvatar(choice: String, imageView: ImageView) {
        selectedChoice = choice
        isAvatarSelected = true
        val imageUrl = getImageUrl(selectedChoice)
        Picasso.get().load(imageUrl).into(binding.selectedImageView)

        // Reset image selection and highlight the selected image
        resetImageSelection()
        // Show avatar selection message
        showSelectionMessage(choice, imageUrl)
    }

    private fun showSelectionMessage(choice: String, imageUrl: String) {
        Picasso.get().load(imageUrl).into(binding.avatarPopupImage)
        binding.selectionMessage.text = "$choice is your champion"
        binding.selectionMessage.visibility = View.VISIBLE
        binding.avatarPopupImage.visibility = View.VISIBLE

        // Shortened animation duration
        binding.avatarPopupImage.scaleX = 0.5f
        binding.avatarPopupImage.scaleY = 0.5f
        binding.avatarPopupImage.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(200) // Reduced to 200ms
            .withEndAction {
                registerUser() // Directly call registerUser after animation
            }

        // Hide message and image after a brief period
        Handler().postDelayed({
            binding.selectionMessage.visibility = View.GONE
            binding.avatarPopupImage.visibility = View.GONE
        }, 3000)
    }

    private fun registerUser() {
        val email = intent.getStringExtra("email")?.trim() ?: ""
        val password = intent.getStringExtra("password")?.trim() ?: ""
        val username = intent.getStringExtra("username")?.trim() ?: ""
        val age = intent.getStringExtra("age")?.toIntOrNull() ?: 0
        val gender = intent.getStringExtra("gender")?.trim() ?: ""
        val location = intent.getStringExtra("location")?.trim() ?: ""
        val income = intent.getStringExtra("income")?.trim() ?: ""

        Log.d("RegisterRequest", "Username: $username, Email: $email, Password: $password, Age: $age, Gender: $gender, Location: $location, Income: $income, Avatar: $selectedChoice")

        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isAvatarSelected) {
            Toast.makeText(this, "Please select an avatar.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the registration request
        val registerRequest = RegisterRequest(
            username = username,
            email = email,
            password = password,
            age = age,
            gender = gender,
            location = location,
            income = income,
            avatar = selectedChoice
        )

        val apiInterface = ApiClient.retrofit.create(SigninInterface::class.java)
        apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ChampionActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ChampionActivity, AwardActivity::class.java))
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Response Code: ${response.code()}, Error Body: $errorBody")
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

    private fun resetImageSelection() {
        binding.Imgshadow.background = null
        binding.imageView3.background = null
        binding.imageView4.background = null
        binding.imageView5.background = null
        binding.selectionMessage.visibility = View.GONE
        binding.avatarPopupImage.visibility = View.GONE
    }
}
