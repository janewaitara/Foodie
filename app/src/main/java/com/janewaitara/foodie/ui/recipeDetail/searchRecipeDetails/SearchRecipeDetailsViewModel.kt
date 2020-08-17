package com.janewaitara.foodie.ui.recipeDetail.searchRecipeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janewaitara.foodie.model.response.RecipeInformationResponse
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi
import kotlinx.coroutines.launch

class SearchRecipeDetailsViewModel (private val remoteApi: RemoteApi): ViewModel(){

    /**Will be used to send generated creatures to the view layer
     *
     * Help to communicate back when the save of a creature is complete
     * Save liveData property to handle communication*/
    private val recipeDetailsLiveData = MutableLiveData<RecipeInformationResponse>()

    fun getRecipeDetailsLiveData(): LiveData<RecipeInformationResponse> = recipeDetailsLiveData

    fun getRecipeDetailsFromApiUsingRecipeId(recipeId: Int){
        viewModelScope.launch {

            val searchedRecipesInfo = remoteApi.getRecipeInformation(recipeId)

            //Log.d("Recipe Information", searchedRecipesInfo.toString())

            if (searchedRecipesInfo is Success) {
                recipeDetailsLiveData.postValue(searchedRecipesInfo.data)
            }
        }
    }

    /**LiveData member variable to cache the list of similar recipes*/
    private val similarRecipeLiveData = MutableLiveData<List<SimilarRecipeResponse>>()

    fun getSimilarRecipesLiveData(): LiveData<List<SimilarRecipeResponse>> = similarRecipeLiveData

    fun getSimilarRecipe(recipeId: Int){
        viewModelScope.launch {
            val similarRecipeList = remoteApi.getSimilarRecipe(recipeId)

            if (similarRecipeList is Success) {
                similarRecipeLiveData.postValue(similarRecipeList.data)
            }
        }
    }
}