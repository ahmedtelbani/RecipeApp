package com.example.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE email = :userEmail)")
    suspend fun userExistsByEmail(userEmail: String): Boolean

    @Query("SELECT * FROM user WHERE email = :email AND hashedPassword = :hashedPassword")
    suspend fun userLogin(email: String, hashedPassword: String): User?

    @Query("SELECT * FROM user where email = :email")
    suspend fun getUserByEmail(email:String):User?

}