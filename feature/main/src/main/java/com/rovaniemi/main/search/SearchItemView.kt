package com.rovaniemi.main.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rovaniemi.main.R
import com.rovaniemi.main.model.SearchViewData
import com.rovaniemi.ui.designsystem.image.CoilImageView
import com.rovaniemi.ui.extension.rippleClickable

// todo storage와 search 같이 쓸 수 있도록 변경하기
@Composable
internal fun SearchItemView(
    modifier: Modifier = Modifier,
    viewData: SearchViewData,
    onClickUpdateBookmark: (item: SearchViewData) -> Unit,
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

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CoilImageView(
            modifier = Modifier
                .width(200.dp),
            imageUrl = viewData.thumbnail,
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = viewData.dateTime,
        )

        Icon(
            modifier = Modifier
                .rippleClickable(
                    shape = RoundedCornerShape(8.dp),
                    onClick = { onClickUpdateBookmark(viewData) },
                ),
            painter = painterResource(id = bookmarkIcon),
            tint = if (viewData.isBookmark) Color.Black else Color.LightGray,
            contentDescription = null,
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