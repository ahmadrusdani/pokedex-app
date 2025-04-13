package com.amd.pokedexapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonListEntity>)

    @Query("SELECT * FROM pokemon")
    fun getAll(): PagingSource<Int, PokemonListEntity>

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :query || '%'")
    fun searchPokemon(query: String): PagingSource<Int, PokemonListEntity>
}