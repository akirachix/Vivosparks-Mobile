package com.akirachix.investikaTrial.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.AssessmentResponse
import com.akirachix.investikaTrial.models.VirtualCoin
import com.akirachix.investikatrial.R
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button
    private lateinit var buttonD: Button
    private lateinit var coinContainer: FrameLayout
    private lateinit var totalCoinsTextView: TextView

    private var currentQuestionIndex = 0
    private var assessments: List<AssessmentResponse> = listOf()
    private var totalCoins = 0

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)

        // Initialize views
        questionTextView = findViewById(R.id.tvQuestion)
        imageView = findViewById(R.id.imageQuestion)
        buttonA = findViewById(R.id.btnOptionA)
        buttonB = findViewById(R.id.btnOptionB)
        buttonC = findViewById(R.id.btnOptionC)
        buttonD = findViewById(R.id.btnOptionD)
        coinContainer = findViewById(R.id.coin_container)
        totalCoinsTextView = findViewById(R.id.tvTotalCoins)

        fetchAssessments()
    }

    private fun fetchAssessments() {
        val call = ApiClient.assessmentApi.getAssessments(listOf(8, 4))

        call.enqueue(object : Callback<List<AssessmentResponse>> {
            override fun onResponse(call: Call<List<AssessmentResponse>>, response: Response<List<AssessmentResponse>>) {
                if (response.isSuccessful) {
                    // Get the list of assessments and take only the first 4
                    assessments = (response.body() ?: emptyList()).take(4)
                    Log.d("API Response", "Number of questions fetched: ${assessments.size}")

                    if (assessments.isNotEmpty()) {
                        displayQuestion()
                    } else {
                        Toast.makeText(this@AssessmentActivity, "No questions available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AssessmentActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<AssessmentResponse>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch data: ${t.message}")
                Toast.makeText(this@AssessmentActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayQuestion() {
        // Check if currentQuestionIndex is within the assessments list
        if (currentQuestionIndex < assessments.size) {
            val question = assessments[currentQuestionIndex]
            questionTextView.text = question.question_text
            Glide.with(this).load(question.question_image).into(imageView)

            // Ensure the answer array is valid (has at least 4 answers)
            if (question.answers.size >= 4) {
                buttonA.text = question.answers[0].text
                buttonB.text = question.answers[1].text
                buttonC.text = question.answers[2].text
                buttonD.text = question.answers[3].text
            }

            setAnswerListeners()
        } else {
            // If no more questions, navigate to the results activity
            navigateToResults()
        }
    }

    private fun setAnswerListeners() {
        buttonA.setOnClickListener { awardCoinsAndProceed() }
        buttonB.setOnClickListener { awardCoinsAndProceed() }
        buttonC.setOnClickListener { awardCoinsAndProceed() }
        buttonD.setOnClickListener { awardCoinsAndProceed() }
    }


    private fun awardCoinsAndProceed() {
        // Award 5 coins for every answer
        awardCoins(5)

        // Show custom Toast with larger text size and better visibility
        showCustomToast("Good Job!!!")

        // Increment to the next question
        currentQuestionIndex++
        displayQuestion() // Display next question
    }

    // Method to show custom Toast
    private fun showCustomToast(message: String) {
        // Inflate custom layout for Toast
        val toastLayout = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        // Find the TextView in the custom layout and set the message
        val toastTextView = toastLayout.findViewById<TextView>(R.id.toast_text)
        toastTextView.text = message

        // Create and display the Toast
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastLayout
        toast.show()
    }


    // Function to award a specific number of coins
    private fun awardCoins(coinsToAward: Int) {
        totalCoins += coinsToAward // Add awarded coins to totalCoins
        runOnUiThread {
            totalCoinsTextView.text = "Total Coins: $totalCoins" // Update UI on main thread
        }

        // Play the coin drop sound
        playCoinDropSound()

        showCoinAnimation(coinsToAward)
    }

    // Function to play the coin drop sound
    private fun playCoinDropSound() {
        // Initialize MediaPlayer with the coin drop sound
        mediaPlayer = MediaPlayer.create(this, R.raw.coin_drop)
        mediaPlayer?.start() // Start playing the sound

        mediaPlayer?.setOnCompletionListener {
            it.release() // Release the MediaPlayer once done
            mediaPlayer = null // Clear the reference
        }
    }

    // Function to show coin falling animation based on awarded coins
    private fun showCoinAnimation(coinsToAward: Int) {
        for (i in 1..coinsToAward) {
            val coinImageView = ImageView(this)
            coinImageView.setImageResource(R.drawable.coin) // Replace with your actual coin image

            // Set the desired width and height for the coin (in pixels)
            val width = dpToPx(50)  // Set width to 50dp
            val height = dpToPx(50) // Set height to 50dp

            // Set the layout parameters for the ImageView
            val layoutParams = FrameLayout.LayoutParams(width, height)
            coinImageView.layoutParams = layoutParams

            coinContainer.addView(coinImageView)

            // Random horizontal position to make the coin animation more natural
            val startX = (coinContainer.width * Math.random()).toFloat()

            // Set up a falling animation for the coin
            val fallAnimation = ObjectAnimator.ofFloat(
                coinImageView,
                "translationY",
                -coinContainer.height.toFloat(),
                coinContainer.height.toFloat()
            )
            fallAnimation.duration = (1500 + (500 * Math.random()).toLong()) // Randomize duration for each coin
            fallAnimation.start()

            // Animate coin horizontally (optional: to add some horizontal movement)
            val horizontalMovement = ObjectAnimator.ofFloat(
                coinImageView,
                "translationX",
                startX,
                startX + (50 * Math.random()).toFloat()
            )
            horizontalMovement.duration = fallAnimation.duration
            horizontalMovement.start()

            fallAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    coinContainer.removeView(coinImageView) // Remove coin when the animation ends
                }
            })
        }
    }

    // Function to convert dp to pixels
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // Navigate to results activity after last question
    private fun navigateToResults() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
