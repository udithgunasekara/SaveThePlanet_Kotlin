package com.example.savtheplanet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighScoreManager(context: Context) : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "GamePrefs"
        private const val HIGH_SCORE_KEY = "highScore"
    }


    fun saveHighScore(highScore: Int) {
        sharedPreferences.edit().putInt(HIGH_SCORE_KEY, highScore).apply()
    }

    fun getHighScore(): Int {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
    }
}