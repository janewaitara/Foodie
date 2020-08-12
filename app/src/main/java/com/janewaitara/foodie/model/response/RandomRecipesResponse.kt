package com.janewaitara.foodie.model.response

import com.janewaitara.foodie.model.data.Recipe
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RandomRecipesResponse(
    val recipes: List<Recipe> = mutableListOf())

