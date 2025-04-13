package com.amd.pokedexapp.presentation.page.pokemon.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity
import com.amd.pokedexapp.domain.model.resource.Resource
import com.amd.pokedexapp.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonInfo = MutableStateFlow<Resource<PokemonEntity>>(Resource.Loading)
    val pokemonInfo = _pokemonInfo.asStateFlow()

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            getPokemonDetailUseCase(pokemonName).collect {
                _pokemonInfo.value = it
            }
        }
    }

}