package com.example.savtheplanet

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {
    private lateinit var layout: FrameLayout
    private val asteroids = mutableListOf<Asteroid>()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var earthImageView: ImageView
    private var running = true
    private var score = 0
    private var eTime = 0L
    private var hScore = 0
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView
    private lateinit var highScoreManager: HighScoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        layout = findViewById(R.id.gameLayout)
        scoreTextView = findViewById(R.id.scoreTextView)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        earthImageView = findViewById(R.id.earthImage)
        startGameLoop()


        highScoreManager = HighScoreManager(this)
    }

    private fun startGameLoop() {
        handler.post(object : Runnable {
            override fun run() {
                if (running) {
                    moveAsteroids()
                    checkCollisions()
                    generateAsteroids()
                    handler.postDelayed(this, 100)
                }
            }
        })
    }

    private fun moveAsteroids() {
        val iterator = asteroids.iterator()
        while (iterator.hasNext()) {
            val asteroid = iterator.next()
            val view = layout.findViewWithTag<ImageView>(asteroid)
            view?.let {
                it.translationY += asteroid.speed
//                if (it.translationY > layout.height) {  // Off the screen checking
//                    layout.removeView(it)
//                    iterator.remove()
//                }
            }
        }
    }

    private fun generateAsteroids() {
        if ((1..100).random() > 98) {
            val asteroid = Asteroid(
                this,
                Asteroid.randomXPosition(layout.width),
                0,
                Asteroid.randomSpeed(eTime)
            )
            val asteroidView = ImageView(this)
            asteroidView.setImageDrawable(asteroid.image)

            asteroidView.layoutParams = FrameLayout.LayoutParams(200,200)

            asteroidView.x = asteroid.x.toFloat()
            asteroidView.y = asteroid.y.toFloat()
            asteroidView.tag = asteroid

            layout.addView(asteroidView)
            asteroids.add(asteroid)
            setupTouchListener(asteroidView, asteroid)
        }
    }

    private fun explodeAsteroid(asteroidView: ImageView, asteroid: Asteroid) {
        asteroidView.animate().alpha(0f).setDuration(300).withEndAction {
            layout.removeView(asteroidView)
            asteroids.remove(asteroid)
        }.start()
        updateScore(5)  // each asteroid gives 5 points
    }

    private fun updateScore(points: Int) {
        score += points
        scoreTextView.text = "Score: $score"
        hScore= highScoreManager.getHighScore()

        highScoreTextView.text = "High Score is: $hScore"

        // Update high score if the current score is higher
        if (score > highScoreManager.getHighScore()) {
            highScoreManager.saveHighScore(score)


        }
    }
    private fun checkCollisions() {
       val earthRect = Rect()

        earthImageView.getHitRect(earthRect) // Get the bounds of the Earth image

        val iterator = asteroids.iterator()
        while (iterator.hasNext()) {
            val asteroid = iterator.next()
            val asteroidView = layout.findViewWithTag<ImageView>(asteroid)

            if (asteroidView != null) { // check visibility
                val asteroidRect = Rect()
                asteroidView.getHitRect(asteroidRect) // Get the bounds of the asteroid image

                if (Rect.intersects(earthRect, asteroidRect)) {
                    gameOver() // Call game over method if an asteroid hits the Earth
                    break
                }
            }
        }
    }

    private fun gameOver() {
        running = false // Stop the game loop

        // "Game Over"
        runOnUiThread {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Earth has been hit by an asteroid!")
                .setPositiveButton("Home") { dialog, _ ->
                    navigateToHome()
                }

            val alert = dialogBuilder.create()
            alert.setTitle("Game Over")
            alert.show()
        }
    }

    private fun navigateToHome(){
        val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
         finish()
    }

        private fun setupTouchListener(asteroidView: ImageView, asteroid: Asteroid) {
            asteroidView.setOnClickListener {
                explodeAsteroid(asteroidView, asteroid)
            }
        }

}


