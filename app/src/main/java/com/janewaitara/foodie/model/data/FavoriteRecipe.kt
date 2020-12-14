package com.janewaitara.foodie.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes_table")
data class FavoriteRecipe(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recipe title") val title: String,
    @ColumnInfo(name = "recipe image")val image: String?,
    val readyInMinutes: Int,
    val healthScore: Double,
    val servings : Int,
    val analyzedInstructions : List<Steps>,
    val extendedIngredients: List<Ingredient>
)