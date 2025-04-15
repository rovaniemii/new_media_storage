package com.rovaniemi.main.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rovaniemi.ui.designsystem.loading.LottieAnimationView

@Composable
internal fun SearchLoadingView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimationView(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    size = 60.dp,
                ),
            jsonString = "rocket_loading.json",
        )
    }
}

@Preview
@Composable
private fun PreviewSearchLoadingView(){
    SearchLoadingView()
}