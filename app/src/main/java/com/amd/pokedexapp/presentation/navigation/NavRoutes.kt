package com.amd.pokedexapp.presentation.navigation

sealed class NavRoutes(val route: String) {
    data object Login : NavRoutes("login")
    data object Home : NavRoutes("home")
    data object PokemonList : NavRoutes("pokemon_list")
    data object Profile : NavRoutes("profile")
    data class PokemonDetail(val pokemonId: Int? = null) :
        NavRoutes("pokemon_detail/{$DOMINANT_COLOR}/{$POKEMON_NAME}") {
        companion object {
            const val DOMINANT_COLOR = "dominantColor"
            const val POKEMON_NAME = "pokemonName"
            fun createRoute(dominantColor: Int, name: String) = "pokemon_detail/$dominantColor/$name"
        }
    }
}