package com.janewaitara.foodie.networking

import android.util.Log
import com.janewaitara.foodie.BuildConfig
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.data.SearchedRecipe
import com.janewaitara.foodie.model.response.*
import com.janewaitara.foodie.model.results.Failure
import com.janewaitara.foodie.model.results.Result
import com.janewaitara.foodie.model.results.Success


class RemoteApi(private val remoteApiService: RemoteApiService) {
   private val apiKey  = BuildConfig.API_KEY
    private val numberOfRecipes = 10

    /**get Random Recipes*/
    suspend fun getRandomRecipes():Result<List<Recipe>> = try {
        Log.d("DATA", "data.recipes[1].instructions")

        val data = remoteApiService.getRandomRecipes(apiKey, numberOfRecipes)

        Log.d("DATA", "${data.recipes[1].image} \n ${data.recipes[1].extendedIngredients[1].image}" )

        Success(data.recipes)
    } catch (error: Throwable){
        Failure(error)
    }

    /**
     * Search Recipes*/
    suspend fun searchRecipe(searchParameter: String): Result<List<SearchedRecipe>> = try {
        val data = remoteApiService.searchRecipes(apiKey, searchParameter)

        Success(data.results)
    } catch (error: Throwable){
        Failure(error)
    }

    /**
     * Get Recipe Information*/
    suspend fun getRecipeInformation(recipeId: Int): Result<RecipeInformationResponse> = try {
        val data = remoteApiService.getRecipeInformation(recipeId, apiKey)

        Success(data)
    }catch (error: Throwable){
        Failure(error)
    }

    /**
     * get nutrition widget*/
    suspend fun getRecipeNutritionWidget(recipeId: Int): Result<NutritionWidgetResponse> = try {
        val data = remoteApiService.getRecipeNutritionWidget(recipeId, apiKey)

        Success(data)
    }catch (error: Throwable){
        Failure(error)
    }

    /**
     *get similar recipes*/
    suspend fun getSimilarRecipe(recipeId: Int): Result<SimilarRecipeResponse> = try {
        val data = remoteApiService.getSimilarRecipe(recipeId, apiKey)

        Success(data.random())
    }catch (error: Throwable){
        Failure(error)
    }

    /**get ingredient substitutes*/
    suspend fun getIngredientSubstitutes(ingredientId: Int): Result<IngredientSubstitutesResponse> = try {
        val data = remoteApiService.getIngredientSubstitute(ingredientId, apiKey)

        Success(data)
    }catch (error: Throwable){
        Failure(error)
    }

    /**get food jokes*/
    suspend fun getRandomFoodJoke(): Result<FoodJokesResponse> = try {
        val data = remoteApiService.getRandomFoodJokes(apiKey)

        Success(data)
    }catch (error: Throwable){
        Failure(error)
    }

    /**get food trivia*/
    suspend fun getRandomFoodTrivia(): Result<FoodTriviaResponse> = try {
        val data = remoteApiService.getRandomFoodTrivia(apiKey)

        Success(data)
    }catch (error: Throwable){
        Failure(error)
    }
}