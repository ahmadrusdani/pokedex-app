package com.amd.pokedexapp.domain.usecase

import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity
import com.amd.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(query: String): Flow<List<PokemonListEntity>> {
        return repository.searchPokemon(query)
    }
}