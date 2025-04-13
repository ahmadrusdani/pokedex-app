package com.amd.pokedexapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amd.pokedexapp.data.local.datasource.DataPreference
import com.amd.pokedexapp.presentation.page.home.HomePage
import com.amd.pokedexapp.presentation.page.login.LoginPage
import com.amd.pokedexapp.presentation.page.pokemon.detail.PokemonDetailPage

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    dataPreference: DataPreference
) {
    val isLoggedIn by dataPreference.isLoggedIn.collectAsState(initial = false)
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isLoggedIn) {
        startDestination = if (isLoggedIn) NavRoutes.Home.route else NavRoutes.Login.route
    }

    startDestination?.let { destination ->
        NavHost(navController = navController, startDestination = destination) {
            composable(NavRoutes.Login.route) {
                LoginPage(
                    onLoginSuccess = {
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.Home.route) {
                HomePage(navController)
            }

            composable(
                route = NavRoutes.PokemonDetail().route,
                arguments = listOf(
                    navArgument("dominantColor") {
                        type = NavType.IntType
                    },
                    navArgument("pokemonName") {
                        type = NavType.StringType
                    }
                )
            ) {
                val dominantColor = remember {
                    val color =
                        it.arguments?.getInt(NavRoutes.PokemonDetail.DOMINANT_COLOR)
                    color?.let { Color(it) } ?: Color.White
                }
                val pokemonName = remember {
                    it.arguments?.getString(NavRoutes.PokemonDetail.POKEMON_NAME)
                }
                PokemonDetailPage(
                    dominantColor = dominantColor,
                    pokemonName = pokemonName ?: "Undifined",
                    navController = navController
                )
            }
        }
    }
}