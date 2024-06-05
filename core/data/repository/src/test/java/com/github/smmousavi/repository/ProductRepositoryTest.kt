package com.github.smmousavi.repository

import app.cash.turbine.test
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.network.apiservices.ProductsApiService
import com.github.smmousavi.network.response.ProductResponse
import com.github.smmousavi.network.response.RatingResponse
import com.github.smmousavi.repository.product.DefaultOfflineFirstProductRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @Mock
    private lateinit var productLocalDataSource: ProductLocalDataSource

    @Mock
    private lateinit var productRemoteDataSource: ProductRemoteDataSource

    private lateinit var productRepository: DefaultOfflineFirstProductRepository

    private lateinit var mockWebServer: MockWebServer

    private lateinit var mockProductApiServer: ProductsApiService

    private lateinit var mockSuccessfulResponse: MockResponse

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


    @Before
    fun setUpMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        mockProductApiServer = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ProductsApiService::class.java)
    }

    @Before
    fun setUpMockSuccessfulResponse() {
        mockSuccessfulResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""[
                {
                    "id": 1,
                    "title": "Test Product",
                    "price": 100.0,
                    "description": "Description",
                    "category": "Category",
                    "image": "Image",
                    "rating": { "rate": 4.5, "count": 100 }
                }
            ]""")
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    @Test
    fun fetchProductsFromApi_Success() = runBlocking {
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
