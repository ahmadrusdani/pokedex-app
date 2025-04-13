package com.amd.pokedexapp.domain.usecase

import com.amd.pokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke() = repository.getPokemonList()
}