package com.github.smmousavi.datasource.remote

import com.github.smmousavi.network.model.NetworkProduct

interface ProductRemoteDataSource {

    suspend fun requestAllProducts(): List<NetworkProduct>
}