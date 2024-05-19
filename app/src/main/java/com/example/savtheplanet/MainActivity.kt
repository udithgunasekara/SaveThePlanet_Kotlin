package com.example.savtheplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Reference to the Start Game button
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            // Intent to start the GameActivity
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        // Reference to the Exit button
        val exitButton = findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {
            // Exit the application
            finish()
        }

    }
}