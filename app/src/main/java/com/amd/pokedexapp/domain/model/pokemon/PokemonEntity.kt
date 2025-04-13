package com.amd.pokedexapp.domain.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemonDetail")
data class PokemonEntity(

    @field:SerializedName("types")
    val types: List<TypesItem>,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("stats")
    val stats: List<StatsItem>,

    @field:SerializedName("name")
    val name: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("order")
    val order: Int
)

data class Stat(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("url")
    val url: String
)

data class Type(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("url")
    val url: String
)

data class TypesItem(

    @field:SerializedName("slot")
    val slot: Int,

    @field:SerializedName("type")
    val type: Type
)

data class StatsItem(

    @field:SerializedName("stat")
    val stat: Stat,

    @field:SerializedName("base_stat")
    val baseStat: Int,

    @field:SerializedName("effort")
    val effort: Int
)

data class Sprites(

    @field:SerializedName("front_default")
    val frontDefault: String,
)