package com.janewaitara.foodie

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.janewaitara.foodie.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RecipeApplication : Application() {

    companion object {
        private lateinit var instance: RecipeApplication
        private lateinit var res : Resources

        fun getAppContext(): Context = instance
        fun getResources() = res

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources

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
                    remoteApiModule,
                    databaseModule,
                    repositoryModule,
                    applicationModule,
                    presentationModule
                )
            )
        }
    }

}