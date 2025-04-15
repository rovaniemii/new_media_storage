package com.rovaniemi.main.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rovaniemi.main.R
import com.rovaniemi.main.common.viewdata.SearchViewData
import com.rovaniemi.ui.designsystem.image.CoilImageView
import com.rovaniemi.ui.extension.rippleClickable

@Composable
internal fun SearchItemView(
    modifier: Modifier = Modifier,
    viewData: SearchViewData,
    onClickUpdateBookmark: ((item: SearchViewData) -> Unit)? = null,
) {
    val bookmarkIcon by remember(viewData.isBookmark) {
        mutableIntStateOf(
            if (viewData.isBookmark) {
                R.drawable.icon_bookmark_24
            } else {
                R.drawable.icon_bookmark_border_24
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
            )
            .then(
                if (onClickUpdateBookmark != null) {
                    Modifier.rippleClickable(
                        shape = RoundedCornerShape(8.dp),
                        onClick = { onClickUpdateBookmark(viewData) },
                    )
                } else {
                    Modifier
                }
            )
            .padding(
                all = 8.dp,
            ),
    ) {
        Box {
            CoilImageView(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(
                        shape = RoundedCornerShape(8.dp),
                    ),
                imageUrl = viewData.thumbnail,
            )

            onClickUpdateBookmark?.let {
                Icon(
                    modifier = Modifier
                        .padding(
                            all = 8.dp,
                        )
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = bookmarkIcon),
                    tint = if (viewData.isBookmark) Color.Black else Color.LightGray,
                    contentDescription = null,
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                ),
            text = viewData.dateTime,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewSearchResultComponentView() {
    Surface {
        SearchItemView(
            viewData = SearchViewData(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
            onClickUpdateBookmark = {},
        )
    }
}