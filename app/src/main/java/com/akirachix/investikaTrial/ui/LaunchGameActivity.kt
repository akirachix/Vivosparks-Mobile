package com.akirachix.investikaTrial.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityLaunchgameBinding


class LaunchGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityLaunchgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLaunchgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, AssessmentActivity::class.java)
            startActivity(intent)

        }
    }
}
