package com.amd.pokedexapp.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.amd.pokedexapp.data.local.database.AppDatabase
import com.amd.pokedexapp.data.remote.ApiService
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: ApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, PokemonListEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonListEntity>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.number ?: 0
            }
        }

        return try {
            val response = api.getPokemonList(offset = offset, limit = state.config.pageSize)
            val endOfPaginationReached = response.results.isEmpty()
            val entities = response.results.map { it.toPokemonListEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.pokemonDao().clearAll()
                }
                db.pokemonDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.e("PokemonRemoteMediator", "Error loading data: ${e.message}", e) // Use Log.e for errors
            MediatorResult.Error(e)
        }
    }

    private fun PokemonListEntity.toPokemonListEntity(): PokemonListEntity {
        val number = if (url.endsWith("/")) {
            url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            url.takeLastWhile { it.isDigit() }
        }
        return PokemonListEntity(
            name = name,
            url = url,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png",
            number = number.toInt()
        )
    }
}