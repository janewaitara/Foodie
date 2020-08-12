package com.janewaitara.foodie.networking

import com.janewaitara.foodie.model.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Represent the API calls you can make
 * */
interface RemoteApiService {

    //get random recipe
    @GET("recipes/random")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RandomRecipesResponse

    //search recipe
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") searchParameter: String
    ): SearchRecipeResponse

    //get recipe information of a searched recipe or similar recipes
    @GET("recipes/{recipeId}/information")
    suspend fun getRecipeInformation(
        @Path("recipeId") recipeId : Int,
        @Query("apiKey") apiKey: String
    ): RecipeInformationResponse

    //get nutrition widget
    @GET("recipes/{recipeId}/nutritionWidget.json")
    suspend fun getRecipeNutritionWidget(
        @Path("recipeId") recipeId : Int,
        @Query("apiKey") apiKey: String
    ): NutritionWidgetResponse

    //get similar recipes
    @GET("recipes/{recipeId}/similar")
    suspend fun getSimilarRecipe(
        @Path("recipeId") recipeId : Int,
        @Query("apiKey") apiKey: String
    ): List<SimilarRecipeResponse>


    //get ingredient substitutes
    @GET("food/ingredients/{ingredientId}/substitutes")
    suspend fun getIngredientSubstitute(
        @Path("ingredientId") recipeId : Int,
        @Query("apiKey") apiKey: String
    ): IngredientSubstitutesResponse

    //get food jokes
    @GET("food/jokes/random")
    suspend fun getRandomFoodJokes(
        @Query("apiKey") apiKey: String
    ): FoodJokesResponse

    //get food trivia
    @GET("food/trivia/random")
    suspend fun getRandomFoodTrivia(
        @Query("apiKey") apiKey: String
    ): FoodTriviaResponse
}