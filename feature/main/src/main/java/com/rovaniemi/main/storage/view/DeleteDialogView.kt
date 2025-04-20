package com.rovaniemi.main.storage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rovaniemi.ui.designsystem.dialog.HYDialogView
import com.rovaniemi.ui.extension.DevicePosture
import com.rovaniemi.ui.extension.rippleClickable

@Composable
internal fun DeleteDialogView(
    modifier: Modifier = Modifier,
    devicePosture: DevicePosture,
    onDismissRequest: () -> Unit,
    onClickYes: () -> Unit,
) {
    HYDialogView(
        modifier = modifier,
        devicePosture = devicePosture,
        onDismissRequest = onDismissRequest,
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "해당 아이템을 삭제하겠습니까?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Black,
                    )
                    .rippleClickable(
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            onDismissRequest()
                            onClickYes()
                        }
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                text = "예",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .rippleClickable(
                        shape = RoundedCornerShape(8.dp),
                        onClick = onDismissRequest,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                text = "아니오",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDeleteDialogView(){
    DeleteDialogView(
        devicePosture = DevicePosture.Normal,
        onDismissRequest = {},
        onClickYes = {},
    )
}