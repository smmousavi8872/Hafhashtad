package com.github.smmousavi.repository.product

import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.model.Product
import kotlinx.coroutines.flow.Flow
import com.github.smmousavi.common.result.Result

interface OfflineFirstProductRepository {

    suspend fun fetchAllProducts(): Flow<Result<List<Product>>>

    suspend fun getAllProducts(): Flow<List<Product>>

}