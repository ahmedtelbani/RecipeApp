package com.example.recipeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name: String,
    val email: String,
    val hashedPassword: String,
    val lastLoginTimestamp: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
