package com.amd.pokedexapp.domain.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonListEntity(
    @PrimaryKey val name: String,
    val url: String,
    val image: String,
    val number: Int? = null,
)

data class PokemonListResponse(
    val results: List<PokemonListEntity>
)