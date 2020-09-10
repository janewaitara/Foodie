package com.janewaitara.foodie.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.janewaitara.foodie.repository.RecipeRepository
import org.koin.core.inject

/**
 *Synchronize data every e.g. 6 hour. Use a PeriodicWorkRequest to implement this type of behavior.
 * This worker load data from the API, and then store it in the Database.
 */

/**
 *Implement KoinComponent interface as a way to tell koin that it can inject fields into this class
 * (which is not a lifeCycle Class)*/
class SynchronizeRecipeWorker(appContext: Context, workerParameters: WorkerParameters, private val repository: RecipeRepository) :
    CoroutineWorker(appContext, workerParameters) {

    companion object {
        const val WORK_NAME = "SynchronizeRecipeWorker"
    }
    override suspend fun doWork(): Result {
        repository.clearRecipes()

        return try {
            repository.getRandomRecipesFromApiAndInsetIntoDb()
            repository.getRandomFoodJokeFromApi()

            Result.success()
        }catch (error: Throwable) {
            Result.failure()
        }
    }
}