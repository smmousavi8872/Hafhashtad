package com.github.smmousavi.network.apiprovider

import com.github.smmousavi.network.apiservices.StoreApiService
import javax.inject.Inject

class DefaultStoreApiProvider @Inject constructor(private val storeApi: StoreApiService) :
    StoreApiProvider {
    override fun getProductsApi(): StoreApiService {
        return storeApi
    }
}