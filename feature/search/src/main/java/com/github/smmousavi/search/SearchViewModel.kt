package com.github.smmousavi.search

import androidx.lifecycle.ViewModel
import com.github.smmousavi.domain.GetProductsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductsFromDatabaseUseCase: GetProductsFromDatabaseUseCase,
) : ViewModel() {

}