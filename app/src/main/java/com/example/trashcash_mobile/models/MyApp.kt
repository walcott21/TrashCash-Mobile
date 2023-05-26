package com.example.trashcash_mobile.models

import android.app.Application
import android.content.Context

class MyApp : Application() {

    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}