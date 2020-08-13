package com.janewaitara.foodie.ui.recipeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.response.IngredientSubstitutesResponse
import com.janewaitara.foodie.model.response.RecipeInformationResponse
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi
import com.janewaitara.foodie.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailsViewModel (
    private val remoteApi: RemoteApi,
    private val repository: RecipeRepository): ViewModel(){

    /**LiveData member variable to cache the DetailsViewState*/
    private val detailsViewState = MutableLiveData<DetailsViewState>()

    fun getDetailsViewState() : LiveData<DetailsViewState> = detailsViewState

    fun getRecipeById(recipeId: Int){
        detailsViewState.value = Loading

        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)

            detailsViewState.value = DetailsSuccess(recipe)
        }
    }

    /**Will be used to send generated creatures to the view layer
     *
     * Help to communicate back when the save of a creature is complete
     * Save liveData property to handle communication*/
    private val recipeInfoLiveData = MutableLiveData<RecipeInformationResponse>()

    fun getRecipeDetailsInfoRecipeLiveData(): LiveData<RecipeInformationResponse> = recipeInfoLiveData

    suspend fun getRecipeDetailsFromApiUsingRecipeId(recipeId: Int){
        val searchedRecipesInfo = remoteApi.getRecipeInformation(recipeId)

        //Log.d("Recipe Information", searchedRecipesInfo.toString())

        if (searchedRecipesInfo is Success) {
            recipeInfoLiveData.postValue(searchedRecipesInfo.data)
        }
    }

    /**LiveData member variable to cache the list of similar recipes*/
    private val similarRecipeLiveData = MutableLiveData<SimilarRecipeResponse>()

    fun getSimilarRecipesLiveData(): LiveData<SimilarRecipeResponse> = similarRecipeLiveData

    suspend fun getSimilarRecipe(recipeId: Int){
        val similarRecipeList = remoteApi.getSimilarRecipe(recipeId)

        if (similarRecipeList is Success){
            similarRecipeLiveData.postValue(similarRecipeList.data)
        }
    }


    /**LiveData member variable to cache the  ingredients substitute*/
    private val ingredientSubstituteLiveData = MutableLiveData<IngredientSubstitutesResponse>()

    fun getIngredientSubstituteLiveData(): LiveData<SimilarRecipeResponse> = similarRecipeLiveData

    suspend fun getIngredientSubstitute(ingredientId: Int){
        val ingredientSubstitute = remoteApi.getIngredientSubstitutes(ingredientId)

        if (ingredientSubstitute is Success){
           ingredientSubstituteLiveData.postValue(ingredientSubstitute.data)
        }
    }

}

sealed class DetailsViewState
object Loading: DetailsViewState()
data class DetailsSuccess(val recipe: Recipe?): DetailsViewState()