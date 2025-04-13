package com.amd.pokedexapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amd.pokedexapp.domain.model.pokemon.PokemonEntity
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity

@Database(
    entities = [PokemonListEntity::class, PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailDao(): PokemonDetailDao
}