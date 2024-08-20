package com.example.recipeapp.data.network.api

import com.example.recipeapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    /**
     * Singleton object to provide Retrofit instance.
     */

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api by lazy {
        retrofit.create(RecipeApi::class.java)
    }
}