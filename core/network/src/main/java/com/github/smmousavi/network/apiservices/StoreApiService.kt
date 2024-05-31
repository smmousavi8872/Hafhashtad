package com.github.smmousavi.network.apiservices

import com.github.smmousavi.model.Product
import com.github.smmousavi.response.ServerResponse
import retrofit2.http.GET


interface StoreApiService {
    @GET(value = "products")
    suspend fun getAllProducts(): ServerResponse<List<Product>>

}