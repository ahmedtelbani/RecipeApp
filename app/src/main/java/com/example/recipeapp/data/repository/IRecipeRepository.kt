package com.example.recipeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.network.response.ApiResponse

interface IRecipeRepository {

    suspend fun addFavoriteMeal(meal: Meal)

    suspend fun getAllFavoriteMeals(): List<Meal>
    
    suspend fun deleteFavoriteMeal(meal: Meal)

    // online related
    suspend fun getMealById(id: Int): ApiResponse
    
    fun isMealFavorite(mealId: String): LiveData<Boolean>
    
}