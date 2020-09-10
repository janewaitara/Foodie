package com.janewaitara.foodie.di

import com.janewaitara.foodie.repository.RecipeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RecipeRepository(get(),get()) }
}