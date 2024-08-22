package com.example.recipeapp.data.repository

import com.example.recipeapp.data.database.UserDao
import com.example.recipeapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao): IUserRepository {

    override suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    override suspend fun checkIfUserExistsByEmail(userEmail: String): Boolean = withContext(Dispatchers.IO) {
        userDao.userExistsByEmail(userEmail)
    }

    override suspend fun userLogin(email: String, hashedPassword: String): User? {
        return userDao.userLogin(email, hashedPassword)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

}