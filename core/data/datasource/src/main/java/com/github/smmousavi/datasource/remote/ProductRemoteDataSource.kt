package com.github.smmousavi.datasource.remote

import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity

interface ProductRemoteDataSource {

    suspend fun upsertProducts(products: List<ProductEntity>)

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getProductById(id: Int): ProductEntity

    suspend fun deleteProducts(ids: List<Int>)

    suspend fun getRatingById(id: Int): RatingEntity
}