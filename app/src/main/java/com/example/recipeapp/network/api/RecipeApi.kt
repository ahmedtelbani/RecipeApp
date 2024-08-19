package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.ResponseObject
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    /**
    * Interface defining Retrofit API endpoints.
    */

    @GET("search.php?s=")
    suspend fun getAllMeals(): ResponseObject

    @GET("categories.php")
    suspend fun getCategories(): ResponseObject

    @GET("lookup.php")
    suspend fun getMealById(@Query("i")  mealId: Int): ResponseObject

    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") mealName: String): ResponseObject

    @GET("random.php")
    suspend fun getRandomMeal():ResponseObject
}