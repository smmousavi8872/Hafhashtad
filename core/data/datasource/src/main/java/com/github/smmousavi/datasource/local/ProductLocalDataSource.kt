package com.github.smmousavi.datasource.local

import com.github.smmousavi.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {

    suspend fun upsertProducts(products: List<ProductEntity>)

    suspend fun getAllProducts(): Flow<List<ProductEntity>>

    suspend fun getProductById(id: Int): Flow<ProductEntity>

    suspend fun deleteProducts(ids: List<Int>)

}