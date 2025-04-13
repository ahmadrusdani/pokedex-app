package com.amd.pokedexapp.di

import android.content.Context
import com.amd.pokedexapp.data.local.datasource.DataPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataPreference(@ApplicationContext context: Context): DataPreference {
        return DataPreference(context)
    }
}