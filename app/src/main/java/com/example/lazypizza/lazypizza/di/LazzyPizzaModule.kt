package com.example.lazypizza.lazypizza.di

import com.example.lazypizza.lazypizza.data.remote.FirebaseDataSource
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import com.example.lazypizza.lazypizza.presentation.cart.CartViewModel
import com.example.lazypizza.lazypizza.presentation.detail.DetailViewModel
import com.example.lazypizza.lazypizza.presentation.list.ListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val lazyPizzaModule = module {
    singleOf(::FirebaseDataSource)  bind DataSource::class

    viewModelOf(::ListViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::CartViewModel)
}