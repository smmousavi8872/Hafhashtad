package com.github.smmousavi.domain

import app.cash.turbine.test
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.network.response.ProductResponse
import com.github.smmousavi.network.response.RatingResponse
import com.github.smmousavi.repository.product.OfflineFirstProductRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetAppProductsUseCaseTest {

    @Mock
    private lateinit var mockProductsRepository: OfflineFirstProductRepository

    private lateinit var getAppProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAppProductsUseCase = GetProductsUseCase(mockProductsRepository)
    }

    @Test
    fun invoke_Success() = runBlocking {
        val mockProducts = listOf(
            ProductResponse(
                1,
                "Test Product",
                100.0,
                "Description",
                "Category",
                "Image",
                RatingResponse(4.5, 100)
            )
        )
        `when`(mockProductsRepository.fetchAllProducts()).thenReturn(
            flowOf(
                Result.Success(
                    mockProducts
                )
            )
        )

        getAppProductsUseCase.invoke().test {
            val result = awaitItem()
            assert(result is Result.Success && result.data == mockProducts)
            awaitComplete()
        }
    }

    @Test
    fun invoke_Error() = runBlocking {
        val exception = RuntimeException("API error")
        `when`(mockProductsRepository.fetchAllProducts()).thenReturn(
            flowOf(
                Result.Error(
                    exception
                )
            )
        )

        getAppProductsUseCase.invoke().test {
            val result = awaitItem()
            assert(result is Result.Error && result.exception == exception)
            awaitComplete()
        }
    }

}