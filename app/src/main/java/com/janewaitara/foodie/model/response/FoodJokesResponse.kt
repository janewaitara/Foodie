package com.janewaitara.foodie.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodJokesResponse(
    val text: String)
