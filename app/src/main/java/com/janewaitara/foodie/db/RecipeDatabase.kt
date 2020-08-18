package com.janewaitara.foodie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.model.data.Recipe

@Database(entities = [Recipe::class, FavoriteRecipe::class], version = 1, exportSchema = false)
@TypeConverters(IngredientsConverter::class, StepsConverter::class)
abstract class RecipeDatabase : RoomDatabase(){

    abstract fun recipeDao() : RecipeDao
}

const val DATABASE_NAME = "recipe_database"