package com.example.lazypizza.lazypizza.presentation.detail

sealed interface DetailEvent {
    data object OnItemAddedToCart: DetailEvent
}