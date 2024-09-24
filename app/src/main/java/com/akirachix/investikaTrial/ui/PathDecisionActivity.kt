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
import com.akirachix.investikatrial.R
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PathDecisionActivity : AppCompatActivity() {

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

    // Track the number of correct and incorrect answers
    private var correctAnswers = 0
    private var incorrectAnswers = 0

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
                    assessments = (response.body() ?: emptyList()).takeLast(6)
                    Log.d("API Response", "Number of questions fetched: ${assessments.size}")

                    if (assessments.isNotEmpty()) {
                        displayQuestion()
                    } else {
                        Toast.makeText(this@PathDecisionActivity, "No questions available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PathDecisionActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<AssessmentResponse>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch data: ${t.message}")
                Toast.makeText(this@PathDecisionActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < assessments.size) {
            val question = assessments[currentQuestionIndex]
            questionTextView.text = question.question_text
            Glide.with(this).load(question.question_image).into(imageView)

            if (question.answers.size >= 4) {
                buttonA.text = question.answers[0].text
                buttonB.text = question.answers[1].text
                buttonC.text = question.answers[2].text
                buttonD.text = question.answers[3].text
            }

            setAnswerListeners()
        } else {
            navigateToResults()
        }
    }

    private fun setAnswerListeners() {
        buttonA.setOnClickListener { checkAnswer(0) }
        buttonB.setOnClickListener { checkAnswer(1) }
        buttonC.setOnClickListener { checkAnswer(2) }
        buttonD.setOnClickListener { checkAnswer(3) }
    }

    private fun checkAnswer(selectedIndex: Int) {
        // Define the correct answer indices for each question
        val correctAnswerIndices = listOf(2, 1, 1, 0, 1, 1) // Update these according to your correct answers
        val correctAnswerIndex = correctAnswerIndices[currentQuestionIndex]

        // Check if the selected option corresponds to the correct answer index
        if (selectedIndex == correctAnswerIndex) {
            Toast.makeText(this, "Good job!", Toast.LENGTH_SHORT).show()
            correctAnswers++ // Increment correct answer count
            awardCoins(10)
        } else {
            Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show()
            incorrectAnswers++ // Increment incorrect answer count
        }

        currentQuestionIndex++

        if (currentQuestionIndex < assessments.size) {
            displayQuestion()
        } else {
            navigateToResults()
        }
    }

    private fun awardCoins(coinsToAward: Int) {
        totalCoins += coinsToAward
        runOnUiThread {
            totalCoinsTextView.text = "Total Coins: $totalCoins"
        }

        playCoinDropSound()
        showCoinAnimation(coinsToAward)
    }

    private fun playCoinDropSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.coin_drop)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }

    private fun showCoinAnimation(coinsToAward: Int) {
        for (i in 1..coinsToAward) {
            val coinImageView = ImageView(this)
            coinImageView.setImageResource(R.drawable.coin)

            val width = dpToPx(50)
            val height = dpToPx(50)
            val layoutParams = FrameLayout.LayoutParams(width, height)
            coinImageView.layoutParams = layoutParams

            coinContainer.addView(coinImageView)

            val startX = (coinContainer.width * Math.random()).toFloat()

            val fallAnimation = ObjectAnimator.ofFloat(
                coinImageView, "translationY", -coinContainer.height.toFloat(), coinContainer.height.toFloat()
            )
            fallAnimation.duration = (1500 + (500 * Math.random()).toLong())
            fallAnimation.start()

            val horizontalMovement = ObjectAnimator.ofFloat(
                coinImageView, "translationX", startX, startX + (50 * Math.random()).toFloat()
            )
            horizontalMovement.duration = fallAnimation.duration
            horizontalMovement.start()

            fallAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    coinContainer.removeView(coinImageView)
                }
            })
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // Decide which activity to navigate to
    private fun navigateToResults() {
        if (correctAnswers == 0) {
            val intent = Intent(this, DefeatActivity::class.java)
            startActivity(intent)
        } else {
            // If at least one answer is correct, navigate to SlayDragonActivity
            val intent = Intent(this, SlayDragonActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}