package com.example.recipeapp.util

class AuthHelper(
    private val email: String,
    private val password: String,
    private val confirmedPassword: String
) {

    fun isEmailValid(): Boolean {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())
    }

    fun isPasswordValid(): Boolean {
        return password.matches(
            "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()
        )
    }

    fun arePasswordsMatching(): Boolean {
        return password == confirmedPassword
    }

    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis()
    }

}