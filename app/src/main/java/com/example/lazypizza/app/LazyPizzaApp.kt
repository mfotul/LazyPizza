package com.example.lazypizza.app

import android.app.Application
import com.example.lazypizza.lazypizza.di.lazyPizzaModule
import com.google.firebase.Firebase
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import org.koin.core.context.GlobalContext.startKoin

class LazyPizzaApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )

        startKoin {
            modules(
                lazyPizzaModule
            )
        }
    }
}