package com.janewaitara.foodie.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recipe title") val title: String,
    @ColumnInfo(name = "recipe image")val image: String?,
    val readyInMinutes: Int,
    val healthScore: Double,
    val servings : Int,
    val analyzedInstructions : List<Steps>,
    val extendedIngredients: List<Ingredient>

) : Parcelable