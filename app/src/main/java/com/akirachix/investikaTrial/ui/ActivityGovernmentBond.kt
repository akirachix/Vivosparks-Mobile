package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityAchievementBinding

class ActivityGovernmentBond : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_open_account)

        // Set up the WebView for displaying the YouTube video
        val webView = findViewById<WebView>(R.id.webview)

        // Enable JavaScript (needed for YouTube)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Ensure the WebView opens URLs within the app
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Handle the error (e.g., show a message or log it)
            }
        }

        // Handle JavaScript and monitor the progress of the video loading
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // You can track video load progress here if needed
            }
        }

        // Corrected YouTube embed code
        val youtubeEmbedCode = """
            <html>
                <body>
                    <iframe id="video" width="100%" height="100%" 
                            src="https://www.youtube.com/embed/Saqn77p63cE" 
                            frameborder="0" 
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                            allowfullscreen></iframe>
                    <script>
                        var player;
                        function onYouTubeIframeAPIReady() {
                            player = new YT.Player('video', {
                                events: {
                                    'onStateChange': onPlayerStateChange
                                }
                            });
                        }

                        function onPlayerStateChange(event) {
                            if (event.data == YT.PlayerState.ENDED) {
                                window.Android.onVideoEnd();
                            }
                        }

                        // Load YouTube iframe API
                        var tag = document.createElement('script');
                        tag.src = "https://www.youtube.com/iframe_api";
                        var firstScriptTag = document.getElementsByTagName('script')[0];
                        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
                    </script>
                </body>
            </html>
        """

        // Load the YouTube video in the WebView
        webView.loadDataWithBaseURL(null, youtubeEmbedCode, "text/html", "UTF-8", null)

        // Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.webview)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add a JavaScript interface to handle video end events
        webView.addJavascriptInterface(object {
            @android.webkit.JavascriptInterface
            fun onVideoEnd() {
                // Navigate to the desired activity when the video finishes
                runOnUiThread {
                    val intent = Intent(this@ActivityGovernmentBond, SafeActivity::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                }
            }
        }, "Android")

        // Find and handle the button for manual navigation to another activity
        val navigateToCdButton = findViewById<Button>(R.id.btnNavigateToCd)
        navigateToCdButton.setOnClickListener {
            // Navigate to the desired activity when the button is clicked
            val intent = Intent(this@ActivityGovernmentBond, SafeActivity::class.java)
            startActivity(intent)
        }
    }
}
