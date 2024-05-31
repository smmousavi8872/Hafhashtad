package com.github.smmousavi.network.retrofit

import androidx.tracing.trace
import com.github.smmousavi.network.PRODUCTS_BASE_URL
import com.github.smmousavi.network.apiservices.StoreApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideProductRetrofit(
        networkJson: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): StoreApiService = trace("Trace:ProductRetrofit") {
        Retrofit.Builder()
            .baseUrl(PRODUCTS_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(StoreApiService::class.java)
    }
}