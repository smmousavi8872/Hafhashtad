package com.github.smmousavi.database.di

import com.github.smmousavi.database.HafashtadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesProductDao(database: HafashtadDatabase) = database.productDao()

}