package com.janewaitara.foodie.di

import androidx.work.WorkManager
import com.janewaitara.foodie.worker.SynchronizeRecipeWorker
import com.janewaitara.foodie.worker.WorkManagerHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single { WorkManagerHelper() }

    single { SynchronizeRecipeWorker(androidContext(), get(), get()) }
}