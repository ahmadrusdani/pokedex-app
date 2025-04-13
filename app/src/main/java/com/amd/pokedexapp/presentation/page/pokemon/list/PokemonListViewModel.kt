package com.amd.pokedexapp.presentation.page.pokemon.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.palette.graphics.Palette
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity
import com.amd.pokedexapp.domain.usecase.GetPokemonListUseCase
import com.amd.pokedexapp.domain.usecase.SearchPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _searchResult = MutableStateFlow<List<PokemonListEntity>>(emptyList())
    val searchResult: StateFlow<List<PokemonListEntity>> = _searchResult
    val searchQuery = _searchQuery.asStateFlow()

    init {
        observeSearchQuery()
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pokemonPagingFlow: Flow<PagingData<PokemonListEntity>> =
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.length < 2) {
                    getPokemonListUseCase()
                } else {
                    searchPokemonUseCase(query).map {
                        PagingData.from(it)
                    }
                }
            }.cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    searchPokemonUseCase(query)
                }
                .collectLatest { result ->
                    _searchResult.value = result
                }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish.invoke(Color(colorValue))
            }
        }
    }

}