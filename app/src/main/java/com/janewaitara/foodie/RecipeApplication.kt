package com.janewaitara.foodie

import android.app.Application
import android.content.Context
import com.janewaitara.foodie.di.databaseModule
import com.janewaitara.foodie.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RecipeApplication : Application() {

    companion object {
        private lateinit var instance: RecipeApplication

        fun getAppContext(): Context = instance

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startingKoin()
    }

    /**
     * Dependency injection with Koin*/
    private fun startingKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@RecipeApplication)

            modules(
                listOf(
                    networkModule,
                    databaseModule
                )
            )
        }
    }

}