package com.janewaitara.foodie.ui.recipeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailsViewModel (private val repository: RecipeRepository): ViewModel(){
    private val detailsViewState = MutableLiveData<DetailsViewState>()

    fun getDetailsViewState() : LiveData<DetailsViewState> = detailsViewState

    fun getRecipeById(recipeId: Int){
        detailsViewState.value = Loading
        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)
            detailsViewState.value = DetailsSuccess(recipe)
        }
    }

}

sealed class DetailsViewState
object Loading: DetailsViewState()
data class DetailsSuccess(val recipe: Recipe?): DetailsViewState()