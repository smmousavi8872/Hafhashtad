package com.github.smmousavi.repository.product

import com.github.smmousavi.common.result.Result
import com.github.smmousavi.model.Product
import com.github.smmousavi.network.response.NetworkProduct
import kotlinx.coroutines.flow.Flow

interface OfflineFirstProductRepository {

    suspend fun fetchAllProducts(): Flow<Result<List<NetworkProduct>>>

    suspend fun getAllProducts(): Flow<Result<List<Product>>>

    suspend fun searchProducts(query: String): Flow<List<Product>>

}

