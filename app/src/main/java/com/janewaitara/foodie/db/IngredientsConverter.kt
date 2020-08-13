package com.janewaitara.foodie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.janewaitara.foodie.model.data.Ingredient

class IngredientsConverter {
    @TypeConverter
    fun fromIngredients(ingredientList: List<Ingredient>?): String? =
        Gson().toJson(ingredientList)

    @TypeConverter
    fun toIngredient(ingredientListString: String): List<Ingredient?> =
        Gson().fromJson(ingredientListString, Array<Ingredient>::class.java).toList()

}