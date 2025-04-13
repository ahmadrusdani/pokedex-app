package com.amd.pokedexapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.amd.pokedexapp.domain.model.pokemon.StatsItem
import com.amd.pokedexapp.domain.model.pokemon.TypesItem
import com.amd.pokedexapp.presentation.theme.AtkColor
import com.amd.pokedexapp.presentation.theme.DefColor
import com.amd.pokedexapp.presentation.theme.HPColor
import com.amd.pokedexapp.presentation.theme.SpAtkColor
import com.amd.pokedexapp.presentation.theme.SpDefColor
import com.amd.pokedexapp.presentation.theme.SpdColor
import com.amd.pokedexapp.presentation.theme.TypeBug
import com.amd.pokedexapp.presentation.theme.TypeDark
import com.amd.pokedexapp.presentation.theme.TypeDragon
import com.amd.pokedexapp.presentation.theme.TypeElectric
import com.amd.pokedexapp.presentation.theme.TypeFairy
import com.amd.pokedexapp.presentation.theme.TypeFighting
import com.amd.pokedexapp.presentation.theme.TypeFire
import com.amd.pokedexapp.presentation.theme.TypeFlying
import com.amd.pokedexapp.presentation.theme.TypeGhost
import com.amd.pokedexapp.presentation.theme.TypeGrass
import com.amd.pokedexapp.presentation.theme.TypeGround
import com.amd.pokedexapp.presentation.theme.TypeIce
import com.amd.pokedexapp.presentation.theme.TypeNormal
import com.amd.pokedexapp.presentation.theme.TypePoison
import com.amd.pokedexapp.presentation.theme.TypePsychic
import com.amd.pokedexapp.presentation.theme.TypeRock
import com.amd.pokedexapp.presentation.theme.TypeSteel
import com.amd.pokedexapp.presentation.theme.TypeWater

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TypesItem.parseTypeToColor(): Color {
    return when(this.type.name.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun StatsItem.parseStatToColor(): Color {
    return when(this.stat.name.lowercase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun StatsItem.parseStatToAbbr(): String {
    return when(this.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}