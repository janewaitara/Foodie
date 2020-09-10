package com.janewaitara.foodie.ui.searchRecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.janewaitara.foodie.model.data.SearchedRecipe
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.model.results.Result
import com.janewaitara.foodie.networking.RemoteApi

class SearchRecipeViewModel(private val remoteApi: RemoteApi):ViewModel() {
    /**Will be used to send generated creatures to the view layer
     *
     * Help to communicate back when the save of a creature is complete
     * Save liveData property to handle communication*/
    private val searchedRecipeLiveData = MutableLiveData<Result<List<SearchedRecipe>>>()

    fun getSearchedRecipeLiveData(): LiveData<Result<List<SearchedRecipe>>> = searchedRecipeLiveData

    suspend fun searchRecipeFromApiUsingSearchParameter(searchParameters: String){

        val searchedRecipes = remoteApi.searchRecipe(searchParameters)

        searchedRecipeLiveData.postValue(searchedRecipes)

    }

}