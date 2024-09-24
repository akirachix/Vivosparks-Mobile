package com.akirachix.investikaTrial.ui
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.media.MediaPlayer // Import MediaPlayer for sound
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.VirtualMoney
import com.akirachix.investikaTrial.models.AssessmentResponse
import com.akirachix.investikaTrial.models.VirtualCoin
import com.akirachix.investikatrial.R
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LowRiskActivity : AppCompatActivity() {
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
    private var totalCoins = 0 // Variable to keep track of total coins
    private var correctAnswerCount = 0 // Track the number of correct answers
    private lateinit var coinSoundPlayer: MediaPlayer // MediaPlayer for coin sound
    // List of correct answer indices (0-based)
    private val correctAnswers = listOf(2, 2, 1, 2, 1, 1, 2, 1)
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
        // Initialize MediaPlayer with a sound resource (replace with your sound file)
        coinSoundPlayer = MediaPlayer.create(this, R.raw.coin_drop) // Add coin_sound.mp3 to /res/raw/
        fetchAssessments()
    }
    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        if (coinSoundPlayer.isPlaying) {
            coinSoundPlayer.stop()
        }
        coinSoundPlayer.release()
    }
    private fun fetchAssessments() {
        val call = ApiClient.assessmentApi.getAssessments(listOf(11))
        call.enqueue(object : Callback<List<AssessmentResponse>> {
            override fun onResponse(call: Call<List<AssessmentResponse>>, response: Response<List<AssessmentResponse>>) {
                if (response.isSuccessful) {
                    val allAssessments = response.body() ?: emptyList()
                    Log.d("API Response", "Total questions fetched: ${allAssessments.size}")
                    if (allAssessments.isNotEmpty()) {
                        val startIndex = (allAssessments.size / 2) - 2
                        assessments = allAssessments.slice(startIndex until startIndex + 5)
                        displayQuestion()
                    } else {
                        Toast.makeText(this@LowRiskActivity, "No questions available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LowRiskActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<AssessmentResponse>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch data: ${t.message}")
                Toast.makeText(this@LowRiskActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
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
            navigateToResults() // Navigate to results once all questions are answered
        }
    }
    private fun setAnswerListeners() {
        buttonA.setOnClickListener { checkAnswer(0) }
        buttonB.setOnClickListener { checkAnswer(1) }
        buttonC.setOnClickListener { checkAnswer(2) }
        buttonD.setOnClickListener { checkAnswer(3) }
    }
    private fun checkAnswer(selectedIndex: Int) {
        val correctIndex = correctAnswers[currentQuestionIndex]
        if (selectedIndex == correctIndex) {
            Toast.makeText(this, "Good job!!", Toast.LENGTH_SHORT).show()
            correctAnswerCount++ // Increment the correct answer count
            awardCoins()
        } else {
            Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show()
        }
        currentQuestionIndex++
        displayQuestion()
    }
    private fun awardCoins() {
        val awardedCoins = 5
        totalCoins += awardedCoins
        animateCoinTotal(awardedCoins)
        Toast.makeText(this@LowRiskActivity, "You have been awarded $awardedCoins coins!", Toast.LENGTH_LONG).show()
        // Play the coin sound
        coinSoundPlayer.start()
        showCoinAnimation()
    }
    private fun animateCoinTotal(awardedCoins: Int) {
        val animator = ValueAnimator.ofInt(totalCoins - awardedCoins, totalCoins)
        animator.duration = 1000
        animator.addUpdateListener { valueAnimator ->
            totalCoinsTextView.text = "Total Coins: ${valueAnimator.animatedValue}"
        }
        animator.start()
    }
    private fun showCoinAnimation() {
        val numberOfCoins = 5
        for (i in 1..numberOfCoins) {
            val coinImageView = ImageView(this)
            coinImageView.setImageResource(R.drawable.coin)
            val width = dpToPx(50)
            val height = dpToPx(50)
            val layoutParams = FrameLayout.LayoutParams(width, height)
            coinImageView.layoutParams = layoutParams
            coinContainer.addView(coinImageView)
            val startX = (coinContainer.width * Math.random()).toFloat()
            val fallAnimation = ObjectAnimator.ofFloat(
                coinImageView,
                "translationY",
                -coinContainer.height.toFloat(),
                coinContainer.height.toFloat()
            )
            fallAnimation.duration = (1500 + (500 * Math.random()).toLong())
            fallAnimation.start()
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
                    coinContainer.removeView(coinImageView)
                }
            })
        }
    }
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
    private fun navigateToResults() {
        val intent: Intent
        if (correctAnswerCount >= 4) {
            // Navigate to a "Success" screen if 4 or more answers are correct
            intent = Intent(this, AchievementScreen::class.java)
        } else {
            // Navigate to a different screen if fewer than 4 answers are correct
            intent = Intent(this, FailedActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}