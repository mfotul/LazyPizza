package com.example.lazypizza.lazypizza.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import com.example.lazypizza.lazypizza.domain.pizza.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CartViewModel(
    private val dataSource: DataSource
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val cart = dataSource.getCartItems()
    private var _recommendedProducts = MutableStateFlow(emptyList<Product>())
    private var _state = MutableStateFlow(CartState())

    private var eventChannel = Channel<CartEvent>()
    val events = eventChannel.receiveAsFlow()

    val state =
        combine(_state, _recommendedProducts, cart) { state, recommendedProducts, cart ->
            val cartProductIds = cart.map { it.id }.toSet()
            val filteredRecommendedProducts =
                recommendedProducts.filter { it.id !in cartProductIds }
            state.copy(
                products = cart,
                recommendedProducts = filteredRecommendedProducts,
                totalPrice = cart.sumOf { it.price * it.amount }
            )
        }.onStart {
            if (!hasLoadedInitialData)
                loadInitialData()
            hasLoadedInitialData = true
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartState()
        )

    private fun loadInitialData() {
        viewModelScope.launch {
            combine(
                dataSource.getProducts()
                .map { products -> products.filter { it.category == Category.DRINK } },
                dataSource.getExtraToppings()
            ) { drinks, extraToppings ->
                drinks + extraToppings
            }.take(1)
                .collect { recommendedProducts ->
                    _recommendedProducts.update { recommendedProducts.shuffled() }
                }
        }
    }

    fun onAction(action: CartAction) {
        when (action) {
            CartAction.OnBackClick -> {}
            is CartAction.OnDecrementClick -> decrementAmount(action.id)
            is CartAction.OnDeleteClick -> deleteItem(action.id)
            is CartAction.OnIncrementClick -> incrementAmount(action.id)
            is CartAction.OnAddToCartClick -> addToCart(action.id)
        }
    }

    private fun addToCart(id: String) {
        viewModelScope.launch {
            dataSource.addToCart(id)
            dataSource.updateProductAmount(id, 1)
            eventChannel.send(CartEvent.OnAddItemToCart)
        }
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch {
            val product = dataSource.getCartItem(id).firstOrNull() ?: return@launch
            dataSource.removeFromCart(id)
            if (product is Product.OtherProduct)
                dataSource.updateProductAmount(id, 0)
        }
    }

    private fun decrementAmount(id: String) {
        viewModelScope.launch {
            val product = dataSource.getCartItem(id).firstOrNull() ?: return@launch
            if (product.amount > 1) {
                val newAmount = product.amount - 1
                dataSource.updateCartProductAmount(id, newAmount)
                if (product is Product.OtherProduct)
                    dataSource.updateProductAmount(id, newAmount)
            }
        }
    }

    private fun incrementAmount(id: String) {
        viewModelScope.launch {
            val product = dataSource.getCartItem(id).firstOrNull() ?: return@launch
            val newAmount = product.amount + 1
            dataSource.updateCartProductAmount(id, newAmount)
            if (product is Product.OtherProduct)
                dataSource.updateProductAmount(id, newAmount)
            eventChannel.send(CartEvent.OnAddItemToCart)
        }
    }
}