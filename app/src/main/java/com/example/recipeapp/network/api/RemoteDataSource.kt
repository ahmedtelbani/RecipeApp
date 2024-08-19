package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Categories
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.ResponseObject
import com.example.recipeapp.network.response.ApiResponse
import com.example.recipeapp.network.response.safeApiCall
import com.example.recipeapp.util.isInternetAvailable
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSource(
    private val api: RecipeApi
): IRemoteDataSource {
    override suspend fun getAllMeals(): ApiResponse {
        return safeApiCall { api.getAllMeals() }
    }

    override suspend fun getCategories(): List<Categories> {
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


}