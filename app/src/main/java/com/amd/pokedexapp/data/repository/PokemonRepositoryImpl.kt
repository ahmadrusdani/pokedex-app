package com.amd.pokedexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amd.pokedexapp.data.local.database.AppDatabase
import com.amd.pokedexapp.data.mediator.PokemonRemoteMediator
import com.amd.pokedexapp.data.remote.ApiService
import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity
import com.amd.pokedexapp.domain.model.resource.Resource
import com.amd.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: AppDatabase
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonList(): Flow<PagingData<PokemonListEntity>> {
        val pagingSourceFactory = { db.pokemonDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = 10, enablePlaceholders = true,
                initialLoadSize = 10,
            ),
            remoteMediator = PokemonRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchPokemon(query: String): Flow<List<PokemonListEntity>> = flow {
        val response = api.getPokemonList(limit = 1000, offset = 0)

        val filtered = response.results
            .filter { it.name.contains(query, ignoreCase = true) }
            .map { entry ->
                val number = if (entry.url.endsWith("/")) {
                    entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    entry.url.takeLastWhile { it.isDigit() }
                }

                PokemonListEntity(
                    name = entry.name,
                    url = entry.url,
                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png",
                    number = number.toInt()
                )
            }

        emit(filtered)
    }

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonEntity>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getPokemonDetail(name)
            db.pokemonDetailDao().insertDetail(response)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            val cached = db.pokemonDetailDao().getDetailByName(name)
            if (cached != null) {
                emit(Resource.Success(cached))
            }

            if (cached == null) {
                emit(Resource.Error(e))
            }
        }
    }
}