package com.example.recipeapp.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val valuee :Int = 1
    val currentID :String = "currentLoginUserID"
    fun getValue(): Int {
        val value =preferences.getInt(currentID,0)
        if (value==0){
            setValue(0)
        }
        return value
    }
    fun setValue(value: Int) {
        val editor = preferences.edit()
        editor.putInt(currentID, value)
        editor.apply()
    }
}