package com.rovaniemi.ui.designsystem.image

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CoilImageView(
    modifier: Modifier = Modifier,
    imageUrl: String,
    backgroundColor: Color = Color.LightGray,
    contentScale: ContentScale = ContentScale.Crop,
    alignment: Alignment = Alignment.Center,
) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier
            .background(
                color = backgroundColor,
            ),
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        alignment = alignment,
    )
}