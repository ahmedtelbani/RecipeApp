package com.example.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User): Long

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE email = :userEmail)")
    suspend fun userExistsByEmail(userEmail: String): Boolean

    @Query("SELECT * FROM user WHERE email = :email AND hashedPassword = :hashedPassword")
    suspend fun userLogin(email: String, hashedPassword: String): User?

    @Delete
    suspend fun deleteUser(user: User)
}