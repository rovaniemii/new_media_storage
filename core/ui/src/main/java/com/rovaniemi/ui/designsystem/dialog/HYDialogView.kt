package com.rovaniemi.ui.designsystem.dialog

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rovaniemi.ui.extension.DevicePosture
import com.rovaniemi.ui.extension.rememberDevicePosture

@Composable
fun HYDialogView(
    modifier: Modifier = Modifier,
    devicePosture: DevicePosture,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    backgroundColor: Color = Color.White,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        ),
    ) {
        Column(
            modifier = modifier
                .then(
                    when (devicePosture) {
                        DevicePosture.Normal -> {
                            Modifier.fillMaxWidth()
                        }

                        else -> {
                            Modifier.width(400.dp)
                        }
                    }
                )
                .background(
                    shape = RoundedCornerShape(20.dp),
                    color = backgroundColor,
                )
                .padding(28.dp),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PreviewHYDialogView() {
    HYDialogView(
        devicePosture = rememberDevicePosture(),
        onDismissRequest = {},
        content = {
            Text(
                text = "이것은 다이얼로그 입니다",
            )
        },
    )
}