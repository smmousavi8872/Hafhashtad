package com.github.smmousavi.datasource.remote

import com.github.smmousavi.database.dao.ProductDao
import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity
import javax.inject.Inject

class DefaultProductRemoteDataSource @Inject constructor(
    private val productDao: ProductDao,
) : ProductRemoteDataSource {
    override suspend fun upsertProducts(products: List<ProductEntity>) {
        return productDao.upsertProducts(products)
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }

    override suspend fun getProductById(id: Int): ProductEntity {
        return productDao.getProductById(id)
    }

    override suspend fun deleteProducts(ids: List<Int>) {
        return productDao.deleteProducts(ids)
    }

    override suspend fun getRatingById(id: Int): RatingEntity {
        return productDao.getRatingById(id)
    }

}