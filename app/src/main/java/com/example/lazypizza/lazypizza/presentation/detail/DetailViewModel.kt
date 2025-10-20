package com.example.lazypizza.lazypizza.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.lazypizza.data.pizza.toOtherProductUi
import com.example.lazypizza.lazypizza.data.pizza.toPizzaUi
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import com.example.lazypizza.lazypizza.domain.pizza.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataSource: DataSource
) : ViewModel() {
    var hasLoadedInitialData: Boolean = false

    private val detailRoute: NavigationRoute.Detail = savedStateHandle.toRoute()
    private val id = detailRoute.id

    private val _state = MutableStateFlow(DetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailState()
        )

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.OnNavigateBack -> {}
            is DetailAction.OnIncrementClick -> incrementAmount(action.id)
            is DetailAction.OnDecrementClick -> decrementAmount(action.id)
        }
    }

    private fun incrementAmount(id: Int) {
        viewModelScope.launch {
            val product = dataSource.getProduct(id).firstOrNull() ?: return@launch
            val other = product as? Product.OtherProduct ?: return@launch
            if (other.amount < 3) {
                val amount = product.amount + 1
                dataSource.updateProductAmount(id, amount)
            }
        }
    }

    private fun decrementAmount(id: Int) {
        viewModelScope.launch {
            val product = dataSource.getProduct(id).firstOrNull() ?: return@launch
            val other = product as? Product.OtherProduct ?: return@launch
            if (other.amount > 0) {
                val amount = product.amount - 1
                dataSource.updateProductAmount(id, amount)
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            dataSource.loadImagesUrl()
            val pizza = dataSource.getProduct(id).firstOrNull() as? Product.Pizza ?: return@launch
            _state.update { currentState ->
                val pizzaUi = pizza.toPizzaUi()
                currentState.copy(
                    pizzaUi = pizzaUi,
                    imageURL = pizzaUi.imageUrl
                )
            }
            launch {
                dataSource.getExtraToppings().collectLatest { extraToppings ->
                    _state.update { currentState ->
                        val price = pizza.price + extraToppings.sumOf {
                            it.price * it.amount
                        }
                        currentState.copy(
                            extraToppings = extraToppings.map { topping -> topping.toOtherProductUi() },
                            price = price
                        )
                    }
                }
            }
        }
    }
}

