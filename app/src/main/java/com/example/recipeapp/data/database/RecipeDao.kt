package com.example.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.model.Meal

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun addFavoriteMeal(meal: Meal)

    @Query("SELECT * FROM FavoriteMeals")
    suspend fun getAllFavoriteMeals(): List<Meal>

    @Query("SELECT idMeal FROM FavoriteMeals")
    suspend fun getAllFavoriteMealsId(): List<String>

    @Delete
    suspend fun deleteFavoriteMeal(meal: Meal)

}