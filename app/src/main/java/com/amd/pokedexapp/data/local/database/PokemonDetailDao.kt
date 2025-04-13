package com.amd.pokedexapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity

@Dao
interface PokemonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemonDetail WHERE name = :name LIMIT 1")
    suspend fun getDetailByName(name: String): PokemonEntity?
}