package com.example.aimovies.application

import android.app.Application
import com.example.aimovies.di.dataModule
import com.example.aimovies.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by A.Elkhami on 18/07/2023.
 */
class AiMoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AiMoviesApp)
            modules(dataModule, homeModule)
        }
    }
}