package com.amd.pokedexapp.domain.usecase

import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity
import com.amd.pokedexapp.domain.model.resource.Resource
import com.amd.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<Resource<PokemonEntity>> {
        return repository.getPokemonDetail(name)
    }
}