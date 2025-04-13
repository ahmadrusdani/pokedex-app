package com.amd.pokedexapp.domain.repository

import androidx.paging.PagingData
import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity
import com.amd.pokedexapp.domain.model.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<PokemonListEntity>>
    fun searchPokemon(query: String): Flow<List<PokemonListEntity>>
    fun getPokemonDetail(name: String): Flow<Resource<PokemonEntity>>
}