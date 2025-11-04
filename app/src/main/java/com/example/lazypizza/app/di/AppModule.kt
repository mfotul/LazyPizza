package com.example.lazypizza.app.di

import com.example.lazypizza.app.LazyPizzaApp
import com.example.lazypizza.app.navigation.NavigationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single {
        (androidApplication() as LazyPizzaApp).applicationScope
    }

    viewModelOf(::NavigationViewModel)
}