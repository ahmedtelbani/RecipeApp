package com.example.recipeapp.network.api.responses

import com.example.recipeapp.network.api.MealDTO

data class MultiMealResponse(
    val meals: List<MealDTO>? = emptyList()
)
