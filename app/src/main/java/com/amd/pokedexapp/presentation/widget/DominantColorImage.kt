package com.amd.pokedexapp.presentation.widget

import android.graphics.drawable.Drawable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun DominantColorImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onDominantColorExtracted: ((Drawable?) -> Unit)? = null
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .scale(0.5f)
                        .align(Alignment.Center)
                )
            }

            is AsyncImagePainter.State.Success -> {
                LaunchedEffect(key1 = painter) {
                    launch {
                        val imageResult = painter.imageLoader.execute(painter.request)
                        val drawable = imageResult.drawable
                        if (drawable != null) {
                            onDominantColorExtracted?.invoke(drawable)
                        }
                    }
                }
                SubcomposeAsyncImageContent()
            }

            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}