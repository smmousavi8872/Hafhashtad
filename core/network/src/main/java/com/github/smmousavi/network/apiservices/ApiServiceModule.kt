package com.github.smmousavi.network.apiservices

import androidx.tracing.trace
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    fun provideStoreApiService(retrofit: Retrofit): StoreApiService {
        return trace("Trace:ProductRetrofit") { retrofit.create(StoreApiService::class.java) }
    }
}