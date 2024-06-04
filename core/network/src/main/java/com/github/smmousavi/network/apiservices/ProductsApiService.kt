package com.github.smmousavi.network.apiservices

import com.github.smmousavi.network.response.NetworkProduct
import retrofit2.http.GET


interface ProductsApiService {
    @GET(value = "products")
    suspend fun requestAllProducts(): List<NetworkProduct>
}