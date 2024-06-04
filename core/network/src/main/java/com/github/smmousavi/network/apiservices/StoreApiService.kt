package com.github.smmousavi.network.apiservices

import com.github.smmousavi.model.NetworkProduct
import com.github.smmousavi.response.ServerResponse
import retrofit2.http.GET


interface StoreApiService {
    @GET(value = "products")
    suspend fun getAllProducts(): ServerResponse<List<NetworkProduct>>
}