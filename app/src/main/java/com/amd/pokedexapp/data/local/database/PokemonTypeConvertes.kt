package com.amd.pokedexapp.data.local.database

import androidx.room.TypeConverter
import com.amd.pokedexapp.domain.model.pokemon.Sprites
import com.amd.pokedexapp.domain.model.pokemon.StatsItem
import com.amd.pokedexapp.domain.model.pokemon.TypesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromTypesItemList(value: List<TypesItem>): String = gson.toJson(value)

    @TypeConverter
    fun toTypesItemList(value: String): List<TypesItem> =
        gson.fromJson(value, object : TypeToken<List<TypesItem>>() {}.type)

    @TypeConverter
    fun fromStatsItemList(value: List<StatsItem>): String = gson.toJson(value)

    @TypeConverter
    fun toStatsItemList(value: String): List<StatsItem> =
        gson.fromJson(value, object : TypeToken<List<StatsItem>>() {}.type)

    @TypeConverter
    fun fromSprites(value: Sprites?): String = gson.toJson(value)

    @TypeConverter
    fun toSprites(value: String): Sprites =
        gson.fromJson(value, Sprites::class.java)
}