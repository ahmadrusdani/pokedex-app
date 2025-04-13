package com.amd.pokedexapp.presentation.page.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.amd.pokedexapp.presentation.navigation.NavRoutes
import com.amd.pokedexapp.presentation.page.pokemon.list.PokemonListPage
import com.amd.pokedexapp.presentation.page.profile.ProfilePage

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HomePage(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = NavRoutes.PokemonList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.PokemonList.route) {
                PokemonListPage(navController = navController)
            }
            composable(NavRoutes.Profile.route) {
                ProfilePage()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val items = listOf(
        NavRoutes.PokemonList,
        NavRoutes.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen) {
                        is NavRoutes.PokemonList -> Icon(Icons.Default.List, contentDescription = null)
                        is NavRoutes.Profile -> Icon(Icons.Default.Person, contentDescription = null)
                        else -> Icon(Icons.Default.Home, contentDescription = null)
                    }
                },
                label = {
                    when (screen) {
                        is NavRoutes.PokemonList -> Text("Pokemons")
                        is NavRoutes.Profile -> Text("Profile")
                        else -> Text("Unknown")
                    }
                }
            )
        }
    }
}