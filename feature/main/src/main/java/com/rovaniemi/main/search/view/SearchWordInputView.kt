package com.rovaniemi.main.search.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rovaniemi.main.R
import com.rovaniemi.ui.designsystem.input.InputView
import com.rovaniemi.ui.extension.rippleClickable

@Composable
internal fun SearchWordInputView(
    modifier: Modifier = Modifier,
    initValue: String,
    onValueChange: (changedValue: String) -> Unit,
    onSearchButtonClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isShowButton by remember(initValue) { mutableStateOf(initValue.isNotEmpty()) }

    InputView(
        modifier = modifier
            .padding(
                vertical = 12.dp,
            ),
        initValue = initValue,
        hint = stringResource(R.string.search_query_hint_message),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchButtonClick()
                focusManager.clearFocus() // 키보드 내리기
            }
        ),
        iconContent = {
            Spacer(modifier = Modifier.width(8.dp))

            if (isShowButton) {
                Image(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onValueChange("")
                            },
                        ),
                    painter = painterResource(R.drawable.icon_clear_20),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Image(
                modifier = Modifier
                    .rippleClickable(
                        shape = CircleShape,
                        onClick = onSearchButtonClick,
                    )
                    .padding(
                        all = 4.dp,
                    ),
                painter = painterResource(id = R.drawable.icon_search_20),
                contentDescription = null,
            )
        },
    )
}

@Preview
@Composable
private fun PreviewSearchWordInputView() {
    Surface {
        var initValue by remember { mutableStateOf("") }

        SearchWordInputView(
            initValue = initValue,
            onValueChange = {
                initValue = it
            },
            onSearchButtonClick = {},
        )
    }
}