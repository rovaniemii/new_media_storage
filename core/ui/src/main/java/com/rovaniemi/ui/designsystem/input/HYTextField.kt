package com.rovaniemi.ui.designsystem.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HYTextField(
    modifier: Modifier = Modifier,
    initValue: String,
    hint: String,
    onValueChange: (changedValue: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    iconContent: @Composable RowScope.(isFocused: Boolean) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isFocused) {
            Color.White
        } else {
            Color.LightGray
        },
        label = "backgroundColor",
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) {
            Color.Black
        } else {
            Color.Gray
        },
        label = "borderColor",
    )

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 48.dp
            )
            .onFocusChanged { focus ->
                isFocused = focus.isFocused
            },
        value = initValue,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    if (initValue.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            color = Color.Gray,
                            text = hint,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    innerTextField()
                }

                iconContent(isFocused)
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSearchWordInputView() {
    var initValue by remember { mutableStateOf("") }

    Surface {
        HYTextField(
            initValue = initValue,
            hint = "입력해주세요.",
            onValueChange = {
                initValue = it
            },
        )
    }
}