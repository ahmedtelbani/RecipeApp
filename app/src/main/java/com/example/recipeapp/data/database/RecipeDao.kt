package com.example.recipeapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.model.Meal

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMeal(meal: Meal)

    @Query("SELECT * FROM FavoriteMeals WHERE userId = :userId")
    suspend fun getAllFavoriteMeals(userId: String): List<Meal>

    @Query("DELETE FROM FavoriteMeals WHERE idMeal = :mealId AND userId = :userId")
    suspend fun deleteFavoriteMeal(mealId: String, userId: String)

    @Query("SELECT * FROM FavoriteMeals WHERE idMeal = :mealId AND userId = :userId")
    fun isMealFavorite(mealId: String, userId: String): LiveData<Meal?>
}
