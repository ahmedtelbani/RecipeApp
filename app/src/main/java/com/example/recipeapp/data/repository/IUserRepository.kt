package com.example.recipeapp.data.repository

import com.example.recipeapp.data.model.User

interface IUserRepository {

    suspend fun addUser(user: User)

    suspend fun checkIfUserExistsByEmail(userEmail: String): Boolean

    suspend fun userLogin(email: String, hashedPassword: String): User?

    suspend fun getUserByEmail(email:String): User?
    
}