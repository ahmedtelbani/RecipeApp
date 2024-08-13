package com.example.recipeapp.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val valuee :Int = 0

    fun getValue(): Int {
        return preferences.getInt("currentID",valuee)
    }
    fun setValue(value: Int) {
        val editor = preferences.edit()
        editor.putInt("currentID", value)
        editor.apply()
    }
}