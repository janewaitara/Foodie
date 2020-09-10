package com.janewaitara.foodie.model.response

import com.janewaitara.foodie.model.data.SearchedRecipe
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRecipeResponse(
    val results: List<SearchedRecipe> = mutableListOf())
