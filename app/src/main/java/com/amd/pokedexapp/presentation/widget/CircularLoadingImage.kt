package com.amd.pokedexapp.presentation.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun CircularLoadingImage(
    model: Any?,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    var loading by remember { mutableStateOf(true) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            onState = { state ->
                loading = when (state) {
                    is AsyncImagePainter.State.Loading -> true
                    is AsyncImagePainter.State.Success -> false
                    is AsyncImagePainter.State.Error -> false // Or handle error differently
                    is AsyncImagePainter.State.Empty -> false
                }
            }
        )

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    }
}