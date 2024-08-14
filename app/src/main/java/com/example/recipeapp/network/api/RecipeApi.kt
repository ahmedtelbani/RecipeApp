package com.example.recipeapp.network.api

import com.example.recipeapp.network.api.responses.MultiMealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    /**
    * Interface defining Retrofit API endpoints.
    */

    // DON'T USE THIS FUNCTION NOW
    @GET("")
    suspend fun getAllMeals()


    @GET("lookup.php")
    suspend fun getMealById(@Query("i")  mealId: Int): MultiMealResponse


    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") mealName: String): MultiMealResponse


}