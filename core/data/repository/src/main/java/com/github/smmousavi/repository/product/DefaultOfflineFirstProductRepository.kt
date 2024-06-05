package com.github.smmousavi.repository.product

import com.github.smmousavi.asEntity
import com.github.smmousavi.asExternalModel
import com.github.smmousavi.common.network.AppDispatchers
import com.github.smmousavi.common.network.Dispatcher
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.datasource.local.ProductLocalDataSource
import com.github.smmousavi.datasource.remote.ProductRemoteDataSource
import com.github.smmousavi.model.Product
import com.github.smmousavi.network.response.NetworkProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultOfflineFirstProductRepository @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    @Dispatcher(AppDispatchers.IO) val ioDispatcher: CoroutineDispatcher,
) : OfflineFirstProductRepository {

    override suspend fun fetchAllProducts(): Flow<Result<List<NetworkProduct>>> = flow {
        emit(Result.Loading)
        try {
            val networkProducts = productRemoteDataSource.requestAllProducts()
            emit(Result.Success(networkProducts))
        } catch (e: Exception) {
            if (productLocalDataSource.productsCount() > 0) {
                emit((Result.Success(emptyList())))
            } else {
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun getAllProducts(): Flow<Result<List<Product>>> = flow {
        try {
            fetchAllProducts().collect { result ->
                when (result) {
                    Result.Loading -> emit(Result.Loading)
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            productLocalDataSource.upsertProducts(result.data.map { it.asEntity() })
                        }
                        emit(
                            Result.Success(
                                productLocalDataSource.getAllProducts()
                                    .map { it.asExternalModel() })
                        )
                    }

                    is Result.Error -> {
                        emit(Result.Error(result.exception))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(ioDispatcher)
}