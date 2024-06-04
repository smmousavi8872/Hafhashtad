package com.github.smmousavi.repository.product

import com.github.smmousavi.asEntity
import com.github.smmousavi.asExternalModel
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultOfflineFirstProductRepository @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
) : OfflineFirstProductRepository {

    override suspend fun fetchAllProducts(): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)
        try {
            val networkProducts = productRemoteDataSource.requestAllProducts()
            var productsEntity: List<ProductEntity>?
            productLocalDataSource.upsertProducts(networkProducts.map { it.asEntity() }
                .also { productsEntity = it })
            productsEntity?.let { products ->
                emit(Result.Success(products.map { it.asExternalModel() }))
            }

        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getAllProducts(): Flow<List<Product>> =
        productLocalDataSource.getAllProducts().map { list -> list.map { it.asExternalModel() } }

}