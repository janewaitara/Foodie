package com.janewaitara.foodie.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.response.FoodJokesResponse
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi
import com.janewaitara.foodie.repository.RecipeRepository
import com.janewaitara.foodie.worker.WorkManagerHelper
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val repository: RecipeRepository,
    private val workManagerHelper: WorkManagerHelper) : ViewModel() {


    /**Using LiveData has several benefits:
    We can put an observer on the data (instead of polling for changes) and only update the
    the UI when the data actually changes.
    Repository is completely separated from the UI through the ViewModel.
     */


    fun getAllRandomRecipes() = repository.getAllRandomRecipes()

    fun getMoviesFromApi(){
        viewModelScope.launch {
            repository.getRandomRecipesFromApi()
        }
    }

    /**Will be used to send generated creatures to the view layer
     *
     * Help to communicate back when the save of a creature is complete
     * Save liveData property to handle communication*/
    private val randomFoodJokeLiveData = MutableLiveData<FoodJokesResponse>()

    fun getRandomFoodJoke(): LiveData<FoodJokesResponse> = randomFoodJokeLiveData

    fun getRandomFoodJokeFromApi(){
        viewModelScope.launch {
           val randomFoodJoke =  repository.getRandomFoodJokeFromApi()

            if(randomFoodJoke is Success){
                randomFoodJokeLiveData.postValue(randomFoodJoke.data)
            }
        }
    }

    fun setUpSynchronization(){
        workManagerHelper.setUpSynchronization()
    }

    fun stopSynchronization(){
        workManagerHelper.stopSynchronization()
    }


}