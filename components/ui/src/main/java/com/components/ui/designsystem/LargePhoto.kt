package com.components.ui.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun LargePhoto(
    modifier: Modifier = Modifier,
    url: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = url,
            placeholder = rememberVectorPainter(image = Icons.Default.ImageSearch),
            error = rememberVectorPainter(image = Icons.Default.Error),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}
