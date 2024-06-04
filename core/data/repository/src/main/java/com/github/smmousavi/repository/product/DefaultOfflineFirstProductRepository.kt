package com.github.smmousavi.repository.product

import com.github.smmousavi.asEntity
import com.github.smmousavi.asExternalModel
import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.model.Product
import com.github.smmousavi.network.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultOfflineFirstProductRepository @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
) : OfflineFirstProductRepository {

    override suspend fun fetchAllProducts(): Flow<Result<List<ProductEntity>>> = flow {
        emit(Result.Loading)
        try {
            val products = productRemoteDataSource.requestAllProducts()
            productLocalDataSource.upsertProducts(products.map { it.asEntity() })
            emit(Result.Success(products.map { it.asEntity() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getAllProducts(): Flow<List<Product>> =
        productLocalDataSource.getAllProducts().map { list -> list.map { it.asExternalModel() } }

}