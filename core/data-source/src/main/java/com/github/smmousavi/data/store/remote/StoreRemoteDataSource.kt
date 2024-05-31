package com.github.smmousavi.data.store.remote

import com.github.smmousavi.model.Product

interface StoreRemoteDataSource {

    suspend fun getAllProducts(): List<Product>

}