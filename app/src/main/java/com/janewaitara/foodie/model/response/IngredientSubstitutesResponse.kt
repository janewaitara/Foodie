package com.janewaitara.foodie.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientSubstitutesResponse(
    val status: String,
    val message: String,
    val ingredient: String?,
    val substitutes: List<String>?
)
