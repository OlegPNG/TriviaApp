package com.example.triviaapp

import android.app.Application
import com.example.triviaapp.data.AppContainer
import com.example.triviaapp.data.DefaultAppContainer

class TriviaApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}