package com.example.savtheplanet

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class Asteroid(context: Context, val x: Int, val y: Int, val speed: Int) {
    var image: Drawable?

    init {
        // Randomly select one of two images
        val imageRes = if ((1..2).random() == 1) R.drawable.ast6 else R.drawable.ast7
        image = ContextCompat.getDrawable(context, imageRes)
    }


    companion object {
        fun randomXPosition(maxWidth: Int): Int = (0 until maxWidth).random()

        fun randomSpeed(elapsedTime: Long): Int {
            // Increase the maximum speed limit every unit of time
            val maxSpeedIncrease = (elapsedTime / 10).toInt()
            return (5..(30 + maxSpeedIncrease)).random()
        }
    }
}



