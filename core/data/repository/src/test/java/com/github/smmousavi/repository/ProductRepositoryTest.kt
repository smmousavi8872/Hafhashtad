package com.github.smmousavi.repository

import app.cash.turbine.test
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.database.dao.ProductDao
import com.github.smmousavi.datasource.local.DefaultProductLocalDataSource
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.DefaultProductRemoteDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.network.apiservices.ProductsApiService
import com.github.smmousavi.repository.product.DefaultOfflineFirstProductRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModelTest {

    @Mock
    private lateinit var mockProductDao: ProductDao


    private lateinit var mockLocalDataSource: ProductLocalDataSource
    private lateinit var mockRemoteDataSource: ProductRemoteDataSource

    private lateinit var productRepository: DefaultOfflineFirstProductRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockProductApiService: ProductsApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()

        mockProductApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ProductsApiService::class.java)

        mockLocalDataSource = DefaultProductLocalDataSource(mockProductDao)
        mockRemoteDataSource = DefaultProductRemoteDataSource(mockProductApiService)

        productRepository = DefaultOfflineFirstProductRepository(
            mockLocalDataSource,
            mockRemoteDataSource,
            Dispatchers.Unconfined
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchProductsFromApi_Success() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """[
                {
                    "id": 1,
                    "title": "Test Product",
                    "price": 100.0,
                    "description": "Description",
                    "category": "Category",
                    "image": "Image",
                    "rating": { "rate": 4.5, "count": 100 }
                }
            ]"""
            )
        mockWebServer.enqueue(mockResponse)

        productRepository.fetchAllProducts().test {
            assert(awaitItem() is Result.Loading)
            val successResult = awaitItem() as Result.Success
            assert(successResult.data[0].title == "Test Product")
            awaitComplete()
        }
    }


    @Test
    fun fetchProductsFromApi_Error() = runBlocking {
        val response = MockResponse()
            .setResponseCode(500)
            .setBody("""{ "error": "Internal Server Error" }""")
        mockWebServer.enqueue(response)

        productRepository.fetchAllProducts().test {
            assert(awaitItem() is Result.Loading)
            val errorResult = awaitItem()
            assert(errorResult is Result.Error)
            awaitComplete()
        }
    }
}
