package com.janewaitara.foodie.di

import com.janewaitara.foodie.ui.recipeDetail.RecipeDetailsViewModel
import com.janewaitara.foodie.ui.recipeList.RecipeListViewModel
import com.janewaitara.foodie.ui.recipeNutrition.RecipeNutritionViewModel
import com.janewaitara.foodie.ui.searchRecipe.SearchRecipeViewModel
import com.janewaitara.foodie.ui.recipeDetail.searchRecipeDetails.SearchRecipeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { RecipeListViewModel(get(),get()) }
    viewModel { RecipeDetailsViewModel(get(),get()) }
    viewModel { SearchRecipeViewModel(get()) }
    viewModel { RecipeNutritionViewModel(get()) }
    viewModel { SearchRecipeDetailsViewModel(get()) }
}