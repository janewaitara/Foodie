package com.janewaitara.foodie.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.janewaitara.foodie.RecipeApplication
import com.janewaitara.foodie.db.RecipeDao
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi

class RecipeRepository(private val recipeDao: RecipeDao,
                       private val remoteApi: RemoteApi) {

    private suspend fun insertAllRandomRecipes(recipeList: List<Recipe>){
        if (recipeList.isNotEmpty()) {
            recipeDao.insertAllRandomRecipe(recipeList)
            Log.d("Data Inserted 8", recipeList.toString())
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

        Log.d("Data Inserted 5", randomRecipes.toString())

        return if (randomRecipes is Success){
            Log.d("Data Inserted 6", randomRecipes.data.toString())
            insertAllRandomRecipes(randomRecipes.data)


        }else{
            Log.d("Data Inserted 7", randomRecipes.toString())
            Toast.makeText(RecipeApplication.getAppContext(), "Failed to fetch tasks!", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getRandomFoodJokeFromApi() =
        remoteApi.getRandomFoodJoke()
    
    /**
     * Functions for favorite Recipes*/
    
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe){
        recipeDao.insertFavRecipe(favoriteRecipe)
    }
    
    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>> = 
        recipeDao.getFavoriteRecipes()
    
    suspend fun getFavoriteRecipeById(favRecipeId: Int): FavoriteRecipe =
        recipeDao.getFavoriteById(favRecipeId)
    
    suspend fun clearFavoriteRecipes() = recipeDao.clearFavoriteRecipes()



}