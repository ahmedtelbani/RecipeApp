package com.example.recipeapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.recipeapp.data.database.RecipeDao
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.network.api.RemoteDataSource
import com.example.recipeapp.data.network.response.ApiResponse

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val remoteDataSource: RemoteDataSource
) : IRecipeRepository {

    override suspend fun addFavoriteMeal(meal: Meal, userId: String) {
        val mealWithUserId = meal.copy(userId = userId)
        recipeDao.addFavoriteMeal(mealWithUserId)
    }

    override suspend fun getAllFavoriteMeals(userId: String): List<Meal> {
        return recipeDao.getAllFavoriteMeals(userId)
    }

    override suspend fun deleteFavoriteMeal(meal: Meal, userId: String) {
        recipeDao.deleteFavoriteMeal(meal.idMeal, userId)
    }

    override suspend fun getMealById(id: Int): ApiResponse {
        return remoteDataSource.getMealById(id)
    }

    override fun isMealFavorite(mealId: String, userId: String): LiveData<Boolean> {
        return recipeDao.isMealFavorite(mealId, userId).map { it != null }
    }
}
