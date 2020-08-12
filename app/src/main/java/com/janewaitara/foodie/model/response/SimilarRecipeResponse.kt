package com.janewaitara.foodie.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SimilarRecipeResponse(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int
)