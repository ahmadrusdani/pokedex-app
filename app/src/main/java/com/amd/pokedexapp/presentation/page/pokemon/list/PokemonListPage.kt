package com.amd.pokedexapp.presentation.page.pokemon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.amd.pokedexapp.R
import com.amd.pokedexapp.domain.model.pokemon.PokemonListEntity
import com.amd.pokedexapp.presentation.navigation.NavRoutes
import com.amd.pokedexapp.presentation.theme.RobotoCondensed
import com.amd.pokedexapp.presentation.widget.DominantColorImage

@ExperimentalCoilApi
@Composable
fun PokemonListPage(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonPagingItems = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearch = viewModel::onSearchQueryChanged
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(pokemonPagingItems, navController, viewModel)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch.invoke(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PokemonList(
    pokemonPagingItems: LazyPagingItems<PokemonListEntity>,
    navController: NavController,
    viewModel: PokemonListViewModel
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(pokemonPagingItems.itemCount / 2) { rowIndex ->
            PokedexRow(
                rowIndex = rowIndex,
                entries = pokemonPagingItems,
                navController = navController,
                viewModel = viewModel
            )
        }

        item {
            when (pokemonPagingItems.loadState.append) {
                is LoadState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> RetrySection("Failed to load") {
                    pokemonPagingItems.retry()
                }

                else -> {}
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PokeDexEntry(
    entry: PokemonListEntity,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    NavRoutes.PokemonDetail.createRoute(dominantColor.toArgb(), entry.name)
                )
            }
    ) {
        Column {
            DominantColorImage(
                imageUrl = entry.image,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                onDominantColorExtracted = { drawable ->
                    drawable?.let {
                        viewModel.calcDominantColor(it) { result ->
                            dominantColor = result
                        }
                    }
                }
            )
            Text(
                text = entry.name,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: LazyPagingItems<PokemonListEntity>,
    navController: NavController,
    viewModel: PokemonListViewModel
) {
    Row {
        val left = entries[rowIndex * 2]
        if (left != null) {
            PokeDexEntry(left, navController, Modifier.weight(1f), viewModel)
        }

        Spacer(modifier = Modifier.width(16.dp))

        if (entries.itemCount > rowIndex * 2 + 1) {
            val right = entries[rowIndex * 2 + 1]
            if (right != null) {
                PokeDexEntry(right, navController, Modifier.weight(1f), viewModel)
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry.invoke() },
            modifier = Modifier.align(
                CenterHorizontally
            )
        ) {
            Text(text = "Retry")
        }
    }
}