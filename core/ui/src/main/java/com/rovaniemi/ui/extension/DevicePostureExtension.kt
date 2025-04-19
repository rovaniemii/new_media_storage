package com.rovaniemi.ui.extension

import android.content.res.Configuration
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker

/**
 * 디바이스 현재 상태 감지
 */
@Composable
fun rememberDevicePosture(): DevicePosture {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val layoutInfo by WindowInfoTracker.getOrCreate(context).windowLayoutInfo(context)
        .collectAsState(null)

    val foldFeature = layoutInfo?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()
        ?.firstOrNull()

    return when {
        foldFeature == null -> if (isTablet(configuration)) {
            DevicePosture.Flat      // 태블릿인 경우 Flat 으로 간주
        } else {
            DevicePosture.Normal
        }

        isGalaxyFlipDevice() -> DevicePosture.Normal
        foldFeature.state == FoldingFeature.State.HALF_OPENED -> DevicePosture.HalfOpened
        foldFeature.state == FoldingFeature.State.FLAT -> DevicePosture.Flat
        else -> DevicePosture.Normal
    }
}

/**
 * 태블릿인지 여부
 */
private fun isTablet(configuration: Configuration): Boolean {
    val screenWidthDp = configuration.screenWidthDp
    return screenWidthDp >= 600
}

/**
 * 갤럭시 Flip 디바이스인지 확인
 *
 * 갤럭시 Flip 은 SM-F700 와 같은 모델명을 가지기 때문에 이를 확인하여 갤럭시 Flip 인지 확인
 * Flip 디바이스가 별도로 존재하지 않고 삼성에서 사이즈를 조정한 상태로 디바이스를 생산하기때문에
 * 위와 같은 방식으로 처리를 한다.
 *
 * 현재로서는 모델명을 가지고 하는 방식 외에 당장 갤럭시 Flip 을 처리할 수 있는 방법이 없다.
 */
private fun isGalaxyFlipDevice(): Boolean {
    return Build.MODEL.contains("SM-F7")
}

enum class DevicePosture {
    Normal,
    HalfOpened,
    Flat,
}
