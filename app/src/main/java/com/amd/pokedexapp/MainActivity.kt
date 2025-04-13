package com.amd.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amd.pokedexapp.data.local.datasource.DataPreference
import com.amd.pokedexapp.presentation.navigation.AppNavGraph
import com.amd.pokedexapp.presentation.theme.PokeDexAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dataPreference: DataPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDexAppTheme {
                AppNavGraph(dataPreference = dataPreference)
            }
        }
    }
}