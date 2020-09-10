package com.janewaitara.foodie.model.response

import com.janewaitara.foodie.model.data.Nutrient
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NutritionWidgetResponse(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
    val bad: List<Nutrient> = mutableListOf(),
    val good: List<Nutrient> = mutableListOf()

)
