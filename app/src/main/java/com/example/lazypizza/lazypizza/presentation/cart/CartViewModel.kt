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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CartViewModel(
    private val dataSource: DataSource
) : ViewModel() {

    private val _cart = dataSource.getCartItems()
    private val _products = dataSource.getProducts()
    private val _extraToppings = dataSource.getExtraToppings()
    private var _state = MutableStateFlow(CartState())

    private var _eventChannel = Channel<CartEvent>()
    val event = _eventChannel.receiveAsFlow()

    val state =
        combine(_state, _cart, _products, _extraToppings) { state, cart, products, extraToppings ->
            val cartProductIds = cart.map { it.id }.toSet()
            val recommendedProducts = (products.filter { it.category == Category.DRINK } + extraToppings)
                .filter { it.id !in cartProductIds }
                .shuffled()
            state.copy(
                products = cart,
                recommendedProducts = recommendedProducts,
                totalPrice = cart.sumOf { it.price * it.amount }
            )
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartState()
        )

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
            _eventChannel.send(CartEvent.OnAddItemToCart)
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
        }
    }
}