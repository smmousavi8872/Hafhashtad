package com.github.smmousavi.network.apiservices

import com.github.smmousavi.network.response.Result
import com.github.smmousavi.network.model.NetworkProduct
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


interface ProductsApiService {
    @GET(value = "products")
    suspend fun requestAllProducts(): List<NetworkProduct>
}