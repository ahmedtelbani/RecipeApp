package com.example.recipeapp.data.network.api

import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.network.response.ApiResponse
import com.example.recipeapp.data.network.response.safeApiCall

class RemoteDataSource(
    private val api: RecipeApi
): IRemoteDataSource {
    override suspend fun getAllMeals(): ApiResponse {
        return safeApiCall { api.getAllMeals() }
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().categories
    }

    override suspend fun getMealById(mealId: Int): ApiResponse {
        return safeApiCall { api.getMealById(mealId) }
//        return api.getMealById(mealId).meals?.first()
    }

    override suspend fun searchMealsByName(mealName: String): ApiResponse {
        return safeApiCall { api.searchMealsByName(mealName) }
//        return api.searchMealsByName(mealName).meals
    }
    
    override suspend fun getRandomMeal(): ApiResponse {
        return safeApiCall { api.getRandomMeal() }
    }

}