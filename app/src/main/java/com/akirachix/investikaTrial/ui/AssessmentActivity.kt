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
        buttonA.setOnClickListener { awardCoinsAndProceed(buttonA.text.toString()) }
        buttonB.setOnClickListener { awardCoinsAndProceed(buttonB.text.toString()) }
        buttonC.setOnClickListener { awardCoinsAndProceed(buttonC.text.toString()) }
        buttonD.setOnClickListener { awardCoinsAndProceed(buttonD.text.toString()) }
    }

    private fun awardCoinsAndProceed(selectedOptionText: String) {
        awardCoins(5)
        showCustomToast("You've opted for: $selectedOptionText. Nice! Let's move on!")
        currentQuestionIndex++
        displayQuestion()
    }

    private fun showCustomToast(message: String) {
        val toastLayout = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val toastTextView = toastLayout.findViewById<TextView>(R.id.toast_text)
        toastTextView.text = message

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastLayout
        toast.show()
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
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
