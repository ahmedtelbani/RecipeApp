package com.example.recipeapp.data.repository

import com.example.recipeapp.data.database.UserDao
import com.example.recipeapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun checkIfUserExistsByEmail(userEmail: String): Boolean = withContext(Dispatchers.IO) {
        userDao.userExistsByEmail(userEmail)
    }

    suspend fun userLogin(email: String, hashedPassword: String): User? {
        return userDao.userLogin(email, hashedPassword)
    }

}