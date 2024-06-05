package com.github.smmousavi.repository

import app.cash.turbine.test
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.network.response.NetworkProduct
import com.github.smmousavi.network.response.NetworkRating
import com.github.smmousavi.repository.product.DefaultOfflineFirstProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @Mock
    private lateinit var productLocalDataSource: ProductLocalDataSource

    @Mock
    private lateinit var productRemoteDataSource: ProductRemoteDataSource

    private lateinit var productRepository: DefaultOfflineFirstProductRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockitoAnnotations.openMocks(this)
        productRepository =
            DefaultOfflineFirstProductRepository(
                productLocalDataSource,
                productRemoteDataSource,
                Dispatchers.Unconfined
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchProductsFromApi_Success() = runBlocking {
        val mockProducts = listOf(
            NetworkProduct(
                1,
                "Test Product",
                100.0,
                "Description",
                "Category",
                "Image",
                NetworkRating(4.5, 100)
            )
        )
        `when`(productRemoteDataSource.requestAllProducts()).thenReturn(mockProducts)

        productRepository.fetchAllProducts().test {
            assert(awaitItem() is Result.Loading)
            val successResult = awaitItem()
            assert(successResult is Result.Success && successResult.data == mockProducts)
            awaitComplete()
        }
    }

    @Test
    fun fetchProductsFromApi_Error() = runBlocking {
        val exception = RuntimeException("API error")
        `when`(productRemoteDataSource.requestAllProducts()).thenThrow(exception)

        productRepository.fetchAllProducts().test {
            assert(awaitItem() is Result.Loading)
            val errorResult = awaitItem()
            assert(errorResult is Result.Error && errorResult.exception == exception)
            awaitComplete()
        }
    }
}
