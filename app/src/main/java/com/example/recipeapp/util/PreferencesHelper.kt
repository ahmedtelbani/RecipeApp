package com.example.recipeapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    private val currentIDKey: String = "currentLoginUserID"
    private val defaultValue: Int = 0
    fun getValue(): Int {
        setValue(1)
        val value = preferences.getInt(currentIDKey, defaultValue)
        Log.d("PreferencesHelper", "getValue: $value")
        return value
    }

    fun setValue(value: Int) {
        Log.d("PreferencesHelper", "setValue: $value")
        val editor = preferences.edit()
        editor.putInt(currentIDKey, value)
        editor.apply()
    }
}