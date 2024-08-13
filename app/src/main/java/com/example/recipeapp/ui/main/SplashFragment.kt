package com.example.recipeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.R
import com.example.recipeapp.ui.AuthActivity
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.PreferencesHelper


class SplashFragment : AppCompatActivity() {
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        preferencesHelper = PreferencesHelper(this)
        val value = preferencesHelper.getValue()
        Handler(Looper.getMainLooper()).postDelayed({
            if (value == 0) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        },3000)
    }
}
