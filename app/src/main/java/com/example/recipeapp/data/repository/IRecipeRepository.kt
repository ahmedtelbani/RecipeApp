package com.example.recipeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.network.response.ApiResponse

interface IRecipeRepository {

    // Add a favorite meal for a specific user
    suspend fun addFavoriteMeal(meal: Meal, userId: String)

    // Get all favorite meals for a specific user
    suspend fun getAllFavoriteMeals(userId: String): List<Meal>

    // Delete a favorite meal for a specific user
    suspend fun deleteFavoriteMeal(meal: Meal, userId: String)

    // Get meal details by ID (online related)
    suspend fun getMealById(id: Int): ApiResponse

    // Check if a meal is a favorite for a specific user
    fun isMealFavorite(mealId: String, userId: String): LiveData<Boolean>
}