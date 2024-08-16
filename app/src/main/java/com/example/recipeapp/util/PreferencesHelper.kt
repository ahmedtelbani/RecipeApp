package com.example.recipeapp.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val valuee :Int = 1
    val currentID :String = "currentLoginUserID"
    fun getValue(): Long {
        val value =preferences.getLong(currentID,0)
        if (value==0L){
            setValue(0)
        }
        return value
    }
    fun setValue(value: Long) {//here i changed the value to be long instead of int because the user id  is defined as long in the db
        val editor = preferences.edit()
        editor.putLong(currentID, value) //changed the putInt function to putLong since the value is Long
        editor.apply()
    }
}