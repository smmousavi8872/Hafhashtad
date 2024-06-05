package com.github.smmousavi.datasource.local

import com.github.smmousavi.database.entity.ProductEntity

interface ProductLocalDataSource {

    suspend fun upsertProducts(products: List<ProductEntity>)

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getProductById(id: Int): ProductEntity

    suspend fun deleteProducts(ids: List<Int>)

    suspend fun productsCount(): Int

}