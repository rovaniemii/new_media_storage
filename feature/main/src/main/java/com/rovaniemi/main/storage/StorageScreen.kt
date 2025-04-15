package com.rovaniemi.main.storage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rovaniemi.main.common.view.ItemsGridView
import com.rovaniemi.main.common.viewdata.SearchViewData

@Composable
internal fun StorageScreen(
    modifier: Modifier = Modifier,
    navigationHeight: Dp,
    items: List<SearchViewData>,
    deleteItem: (itemId: Long) -> Unit,
) {
    ItemsGridView(
        modifier = modifier,
        navigationHeight = navigationHeight,
        items = items,
        onClickItem = {
            deleteItem(it.id)
        }
    )
}

@Preview
@Composable
private fun PreviewStorageScreen() {
    StorageScreen(
        navigationHeight = 0.dp,
        items = listOf(
            SearchViewData(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
            SearchViewData(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
            SearchViewData(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
        ),
        deleteItem = {},
    )
}