package com.github.smmousavi.domain

import com.github.smmousavi.domain.search.DefaultSearchProductsUseCase
import com.github.smmousavi.repository.product.DefaultOfflineFirstProductRepository
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetSearchUseCaseTest {
    @Mock
    private lateinit var mockProductsRepository: DefaultOfflineFirstProductRepository

    private lateinit var getProductsUseCase: DefaultSearchProductsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductsUseCase = DefaultSearchProductsUseCase(mockProductsRepository)
    }
}