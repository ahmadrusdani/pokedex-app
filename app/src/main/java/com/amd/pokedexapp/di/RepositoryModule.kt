package com.amd.pokedexapp.di

import com.amd.pokedexapp.data.local.database.AppDatabase
import com.amd.pokedexapp.data.remote.ApiService
import com.amd.pokedexapp.data.repository.PokemonRepositoryImpl
import com.amd.pokedexapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: ApiService,
        db: AppDatabase
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, db)
    }
}