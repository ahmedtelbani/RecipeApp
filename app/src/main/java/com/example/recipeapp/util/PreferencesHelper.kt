package com.example.recipeapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val currentID: String = "currentLoginUserID"
  
    fun getValue(): Long {
        val value = preferences.getLong(currentID, 0)
        if (value == 0L){
            setValue(0)
        }
        return value
    }
    
    fun setValue(value: Long) {
        val editor = preferences.edit()
        editor.putLong(currentID, value)
        editor.apply()
    }
    
}