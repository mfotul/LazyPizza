@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.lazypizza.lazypizza.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val PRODUCTS_OFFSET = 3

class ListViewModel(
    private val dataSource: DataSource
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var _search = MutableStateFlow("")
    private val _products = _search.flatMapLatest { search ->
        dataSource.getProducts(search)
    }
    private var _eventChannel = Channel<ListEvent>()
    val event = _eventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(ListState())
    val state = combine(_products, _search, _state) { products, search, state ->
        val groupedProducts = products.groupBy { product ->
            product.category
        }
        val headerIndices = buildMap {
            var index = PRODUCTS_OFFSET
            groupedProducts.forEach { (header, items) ->
                put(header, index)
                index += 1 + items.size
            }
        }
        state.copy(
            search = search,
            products = groupedProducts,
            headersIndices = headerIndices
        )
    }.onStart {
        if (!hasLoadedInitialData) {
            loadInitialData()
            hasLoadedInitialData = true
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ListState()
    )

    fun onAction(action: ListAction) {
        when (action) {
            is ListAction.OnCategoryClick, is ListAction.NavigateToDetail -> {}
            is ListAction.OnDecrementClick -> decrementAmount(action.id)
            is ListAction.OnDeleteClick -> resetAmount(action.id)
            is ListAction.OnIncrementClick -> incrementAmount(action.id)
            is ListAction.OnSearchChange -> searchProducts(action.search)
        }
    }

    private fun searchProducts(newSearch: String) {
        _search.value = newSearch
    }

    private fun incrementAmount(id: String) {
        viewModelScope.launch {
            val product = dataSource.getProduct(id).firstOrNull() ?: return@launch
            val newAmount = product.amount + 1
            dataSource.updateProductAmount(id, newAmount)
            dataSource.updateCartProductAmount(id, newAmount)
            _eventChannel.send(ListEvent.OnItemAddedToCart)
        }
    }

    private fun resetAmount(id: String) {
        viewModelScope.launch {
            dataSource.updateProductAmount(id, 0)
            dataSource.removeFromCart(id)
        }
    }

    private fun decrementAmount(id: String) {
        viewModelScope.launch {
            val product = dataSource.getProduct(id).firstOrNull() ?: return@launch
            val newAmount = product.amount - 1
            dataSource.updateProductAmount(id, newAmount)
            if (newAmount == 0)
                dataSource.removeFromCart(id)
            else
                dataSource.updateCartProductAmount(id, newAmount)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            dataSource.loadInitialData()
        }
    }
}