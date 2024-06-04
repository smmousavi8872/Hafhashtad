package com.github.smmousavi.datasource.remote

import com.github.smmousavi.network.response.NetworkProduct

interface ProductRemoteDataSource {

    suspend fun requestAllProducts(): List<NetworkProduct>
}