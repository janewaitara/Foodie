package com.janewaitara.foodie.ui.favoriteRecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.repository.RecipeRepository

class FavoriteRecipeViewModel(private val repository: RecipeRepository): ViewModel() {

    private val favRecipeViewState = MutableLiveData<FavoriteRecipeViewState>()

    fun getFavRecipeViewState(): LiveData<FavoriteRecipeViewState> = favRecipeViewState

    fun getFavoriteRecipe(){
        favRecipeViewState.value = FavRecipeLoading

        val favoriteRecipes = repository.getAllFavoriteRecipes()

        favRecipeViewState.value = FavRecipeSuccess(favoriteRecipes)

    }

    suspend fun clearFavoriteRecipe() = repository.clearFavoriteRecipes()

}

sealed class FavoriteRecipeViewState
object FavRecipeLoading: FavoriteRecipeViewState()
data class FavRecipeSuccess(val favRecipes: LiveData<List<FavoriteRecipe>>): FavoriteRecipeViewState()