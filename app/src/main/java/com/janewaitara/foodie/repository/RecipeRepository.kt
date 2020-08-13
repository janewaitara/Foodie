package com.janewaitara.foodie.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.janewaitara.foodie.RecipeApplication
import com.janewaitara.foodie.db.RecipeDao
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi

class RecipeRepository(private val recipeDao: RecipeDao,
                       private val remoteApi: RemoteApi) {

    private suspend fun insertAllRandomRecipes(recipeList: List<Recipe>){
        if (recipeList.isNotEmpty()) {
            recipeDao.insertAllRandomRecipe(recipeList)
        }
    }

    /**
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     */
    fun getAllRandomRecipes():LiveData<List<Recipe>> =
        recipeDao.getAllRandomRecipes()


    suspend fun getRecipeById(recipeId: Int): Recipe =
        recipeDao.getRecipeById(recipeId)

    suspend fun clearRecipes(){
        recipeDao.clearRecipes()
    }
    /**
     * Get random recipes from Api*/
    suspend fun getRandomRecipesFromApiAndInsetIntoDb(){
        val randomRecipes = remoteApi.getRandomRecipes()

        return if (randomRecipes is Success){
            insertAllRandomRecipes(randomRecipes.data)

        }else{
            Toast.makeText(RecipeApplication.getAppContext(), "Failed to fetch tasks!", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getRandomFoodJokeFromApi() =
        remoteApi.getRandomFoodJoke()


}