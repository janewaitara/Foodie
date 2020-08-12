package com.janewaitara.foodie.model.response

import com.janewaitara.foodie.model.data.Ingredient
import com.janewaitara.foodie.model.data.Steps
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RecipeInformationResponse(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val healthScore: Double,
    val servings : Int,
    val analyzedInstructions : List<Steps> = mutableListOf(),
    val extendedIngredients: List<Ingredient> = mutableListOf()
)