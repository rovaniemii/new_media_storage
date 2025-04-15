package com.rovaniemi.ui.designsystem.loading

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationView(
    modifier: Modifier = Modifier,
    jsonString: String,
    contentScale: ContentScale = ContentScale.Fit,
    speed: Float = 1.0f,
    restartOnPlay: Boolean = true,
    iterations: Int = LottieConstants.IterateForever,
) {
    val compositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(jsonString)
    )

    compositionResult.value?.let { composition ->
        LottieAnimation(
            modifier = modifier,
            composition = composition,
            contentScale = contentScale,
            speed = speed,
            restartOnPlay = restartOnPlay,
            iterations = iterations
        )
    }
}