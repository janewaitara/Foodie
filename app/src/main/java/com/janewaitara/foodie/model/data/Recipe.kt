package com.janewaitara.foodie.model.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val healthScore: Double,
    val servings : Int,
    val analyzedInstructions : List<Steps>,
    val extendedIngredients: List<Ingredient>

) : Parcelable