package com.github.smmousavi.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.github.smmousavi.domain.search.SearchProductsUseCase
import com.github.smmousavi.model.Product
import com.github.smmousavi.model.Rating
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockSearchProductsUseCase: SearchProductsUseCase

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(mockSearchProductsUseCase)
    }

    @Test
    fun searchProducts_Success() = runTest {
        val mockQuery = "Test"
        val mockProducts = listOf(
            Product(1, "Test Product", 100.0, "Description", "Category", "Image", Rating(4.5, 100))
        )
        `when`(mockSearchProductsUseCase.invoke(mockQuery)).thenReturn(flowOf(mockProducts))

        searchViewModel.updateSearchQuery(mockQuery)
        searchViewModel.searchResults.test {
            val result = awaitItem()
            assert(result == mockProducts)
            awaitComplete()
        }
    }

    @Test
    fun searchProducts_EmptyQuery() = runTest {
        searchViewModel.updateSearchQuery("")
        searchViewModel.searchResults.test {
            val result = awaitItem()
            assert(result.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun searchProducts_Debounce() = runTest {
        val mockQuery = "Test"
        val mockProducts = listOf(
            Product(1, "Test Product", 100.0, "Description", "Category", "Image", Rating(4.5, 100))
        )
        `when`(mockSearchProductsUseCase.invoke(mockQuery)).thenReturn(flowOf(mockProducts))

        searchViewModel.updateSearchQuery("T")
        searchViewModel.updateSearchQuery("Te")
        searchViewModel.updateSearchQuery("Tes")
        searchViewModel.updateSearchQuery("Test")

        searchViewModel.searchResults.test {
            val result = awaitItem()
            assert(result == mockProducts)
            awaitComplete()
        }
    }
}