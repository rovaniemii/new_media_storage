package com.rovaniemi.main.storage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rovaniemi.main.compose.view.ItemsGridView
import com.rovaniemi.main.compose.viewdata.SearchViewData
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun StorageScreen(
    modifier: Modifier = Modifier,
    navigationHeight: Dp,
    cellsCount: Int,
    items: LazyPagingItems<SearchViewData>,
    deleteItem: (itemId: Long) -> Unit,
) {
    ItemsGridView(
        modifier = modifier,
        navigationHeight = navigationHeight,
        cellsCount = cellsCount,
        items = items,
        onClickItem = {
            deleteItem(it.id)
        }
    )
}

@Preview
@Composable
private fun PreviewStorageScreen() {
    fun fakeItems(): List<SearchViewData> = listOf(
        SearchViewData(
            id = 1L,
            thumbnail = "",
            dateTime = "",
            isBookmark = true,
        ),
        SearchViewData(
            id = 1L,
            thumbnail = "",
            dateTime = "",
            isBookmark = false,
        ),
        SearchViewData(
            id = 1L,
            thumbnail = "",
            dateTime = "",
            isBookmark = false,
        ),
    )

    val fakePagingData = flowOf(PagingData.from(fakeItems()))
    val lazyItems = fakePagingData.collectAsLazyPagingItems()

    StorageScreen(
        navigationHeight = 0.dp,
        cellsCount = 2,
        items = lazyItems,
        deleteItem = {}
    )
}