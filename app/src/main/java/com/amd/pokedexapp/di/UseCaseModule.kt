package com.amd.pokedexapp.di

import com.amd.pokedexapp.domain.repository.PokemonRepository
import com.amd.pokedexapp.domain.usecase.GetPokemonDetailUseCase
import com.amd.pokedexapp.domain.usecase.GetPokemonListUseCase
import com.amd.pokedexapp.domain.usecase.SearchPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }


    @Provides
    fun provideGetPokemonLDetailUseCase(repository: PokemonRepository): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCase(repository)
    }


    @Provides
    fun provideSearchPokemonUseCase(repository: PokemonRepository): SearchPokemonUseCase {
        return SearchPokemonUseCase(repository)
    }
}