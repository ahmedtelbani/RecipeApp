package com.example.recipeapp.util

class AuthHelper(var email: String, var password: String, var confirmedPassword: String) {

    fun isEmailValid(): Boolean {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())
    }

    fun isPasswordValid(): Boolean {
        return password.matches(
            "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,15}$".toRegex()
        )
    }

    fun arePasswordsMatching(): Boolean {
        return password == confirmedPassword
    }
}