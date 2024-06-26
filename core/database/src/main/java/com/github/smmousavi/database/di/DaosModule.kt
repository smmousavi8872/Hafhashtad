package com.github.smmousavi.database.di

import com.github.smmousavi.database.SampleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesProductDao(database: SampleDatabase) = database.productDao()

}