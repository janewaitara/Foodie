package com.janewaitara.foodie.ui.recipeNutitrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janewaitara.foodie.model.response.NutritionWidgetResponse
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.RemoteApi
import kotlinx.coroutines.launch

class RecipeNutritionViewModel(private val remoteApi: RemoteApi) : ViewModel(){

    /**LiveData member variable to cache the recipe nutrition*/
    private val recipeNutritionLiveData  = MutableLiveData<NutritionWidgetResponse>()

    fun getRecipeNutritionLiveData(): LiveData<NutritionWidgetResponse> = recipeNutritionLiveData

    fun getRecipeNutrition(recipeId: Int) {
        viewModelScope.launch {

            val recipeNutrition = remoteApi.getRecipeNutritionWidget(recipeId)

            if (recipeNutrition is Success){
                recipeNutritionLiveData.postValue(recipeNutrition.data)
            }
        }
    }



}