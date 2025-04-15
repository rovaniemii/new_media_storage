package com.rovaniemi.ui.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

interface MultipleEventsCutterManager {
    fun processEvent(event: () -> Unit)
}

/**
 * 다중 클릭 방지
 */
@Composable
private fun <T> multipleEventsCutter(
    duration: Long = 1000L,
    content: @Composable (MultipleEventsCutterManager) -> T,
): T {
    val throttleState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content(
        object : MultipleEventsCutterManager {
            override fun processEvent(event: () -> Unit) {
                throttleState.tryEmit(event)
            }
        }
    )

    LaunchedEffect(true) {
        throttleState
            .throttleFirst(duration)
            .collect { onClick ->
                onClick.invoke()
            }
    }

    return result
}

/**
 * 리플 클릭 효과
 */
fun Modifier.rippleClickable(
    shape: Shape,
    backgroundColor: Color = Color.White,
    usingThrottle: Boolean = true,
    duration: Long = 1000L,
    onClick: () -> Unit
): Modifier = composed {
    multipleEventsCutter(
        duration = duration,
    ) { manager ->
        clip(shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    color = backgroundColor.getAdjustedRippleColor(),
                ),
                onClick = {
                    if (usingThrottle) {
                        manager.processEvent { onClick() }
                    } else {
                        onClick()
                    }
                },
            )
    }
}

/**
 * Color 타입에 리플 효과 색상 조정
 */
fun Color.getAdjustedRippleColor(): Color {
    // 확장 함수로 색상을 밝게 또는 어둡게 조정
    fun Color.darken(factor: Float): Color {
        return copy(
            red = (red * (1 - factor)).coerceIn(0f, 1f),
            green = (green * (1 - factor)).coerceIn(0f, 1f),
            blue = (blue * (1 - factor)).coerceIn(0f, 1f)
        )
    }

    fun Color.lighten(factor: Float): Color {
        return copy(
            red = (red + (1 - red) * factor).coerceIn(0f, 1f),
            green = (green + (1 - green) * factor).coerceIn(0f, 1f),
            blue = (blue + (1 - blue) * factor).coerceIn(0f, 1f)
        )
    }

    // 배경색의 명도를 기준으로 리플 색상의 밝기를 조정
    return if (luminance() > 0.5) {
        // 밝은 배경일 경우 약간 더 어두운 색을 리플로 사용
        copy(alpha = 0.2f).darken(0.5f)
    } else {
        // 어두운 배경일 경우 약간 더 밝은 색을 리플로 사용
        copy(alpha = 0.3f).lighten(0.5f)
    }
}