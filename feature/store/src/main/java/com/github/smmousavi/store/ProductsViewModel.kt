package com.github.smmousavi.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.domain.GetProductsUseCase
import com.github.smmousavi.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsFromDatabaseUseCase: GetProductsUseCase,
) : ViewModel() {
    private val _products = MutableStateFlow<Result<List<Product>>>(Result.Loading)
    val products: StateFlow<Result<List<Product>>> get() = _products

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            getProductsFromDatabaseUseCase.invoke().collect { result ->
                _products.value = result
            }
        }
    }
}