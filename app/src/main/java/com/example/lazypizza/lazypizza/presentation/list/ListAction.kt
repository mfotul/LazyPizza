package com.example.lazypizza.lazypizza.presentation.list

import com.example.lazypizza.lazypizza.domain.pizza.Category

sealed interface ListAction {
    data class OnCategoryClick(val category: Category) : ListAction
    data class OnIncrementClick(val id: Int) : ListAction
    data class OnDecrementClick(val id: Int) : ListAction
    data class OnDeleteClick(val id: Int) : ListAction
    data class OnSearchChange(val search: String) : ListAction
    data class NavigateToDetail(val id: Int) : ListAction
}