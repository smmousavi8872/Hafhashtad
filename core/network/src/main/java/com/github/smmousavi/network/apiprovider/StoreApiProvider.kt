package com.github.smmousavi.network.apiprovider

import com.github.smmousavi.network.apiservices.StoreApiService

interface StoreApiProvider {

    fun getProductsApi(): StoreApiService
}