package com.example.recipeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.User

@Database(
    version = 1,
    entities = [User::class, Meal::class]
)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance  = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "Recipe_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}