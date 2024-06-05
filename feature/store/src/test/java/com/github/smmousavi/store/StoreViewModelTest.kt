package com.github.smmousavi.store

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.domain.products.GetProductsUseCase
import com.github.smmousavi.model.Product
import com.github.smmousavi.model.Rating
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockGetProductsUseCase: GetProductsUseCase

    private lateinit var productsViewModel: ProductsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productsViewModel = ProductsViewModel(mockGetProductsUseCase)
    }

    @Test
    fun getAllProducts_Success() = runTest {
        val mockProducts = listOf(
            Product(1, "Test Product", 100.0, "Description", "Category", "Image", Rating(4.5, 100))
        )
        `when`(mockGetProductsUseCase.invoke()).thenReturn(flowOf(Result.Success(mockProducts)))

        productsViewModel.getAllProducts()
        productsViewModel.products.test {
            assert(awaitItem() is Result.Loading)
            val successResult = awaitItem() as Result.Success
            assert(successResult.data == mockProducts)
//            awaitComplete()
        }
    }

    @Test
    fun getAllProducts_Error() = runTest {
        val exception = RuntimeException("API error")
        `when`(mockGetProductsUseCase.invoke()).thenReturn(flowOf(Result.Error(exception)))

        productsViewModel.getAllProducts()
        productsViewModel.products.test {
            assert(awaitItem() is Result.Loading)
            val errorResult = awaitItem() as Result.Error
            assert(errorResult.exception == exception)
//            awaitComplete()
        }
    }

    @Test
    fun refreshProducts_Success() = runTest {
        val mockProducts = listOf(
            Product(1, "Test Product", 100.0, "Description", "Category", "Image", Rating(4.5, 100))
        )
        `when`(mockGetProductsUseCase.invoke()).thenReturn(flowOf(Result.Success(mockProducts)))

        productsViewModel.refreshProducts()
        productsViewModel.products.test {
            assert(awaitItem() is Result.Loading)
            val successResult = awaitItem() as Result.Success
            assert(successResult.data == mockProducts)
//            awaitComplete()
        }
        productsViewModel.isRefreshing.test {
            assert(!awaitItem())
//            awaitComplete()
        }
    }

    @Test
    fun refreshProducts_Error() = runTest {
        val exception = RuntimeException("API error")
        `when`(mockGetProductsUseCase.invoke()).thenReturn(flowOf(Result.Error(exception)))

        productsViewModel.refreshProducts()
        productsViewModel.products.test {
            assert(awaitItem() is Result.Loading)
            val errorResult = awaitItem() as Result.Error
            assert(errorResult.exception == exception)
//            awaitComplete()
        }
        productsViewModel.isRefreshing.test {
            assert(!awaitItem())
//            awaitComplete()
        }
    }
}
