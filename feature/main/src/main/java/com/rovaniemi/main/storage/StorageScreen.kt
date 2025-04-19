package com.rovaniemi.main.storage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.compose.LazyPagingItems
import com.rovaniemi.main.compose.view.ItemsGridView
import com.rovaniemi.main.compose.viewdata.SearchViewData

@Composable
internal fun StorageScreen(
    modifier: Modifier = Modifier,
    navigationHeight: Dp,
    items: LazyPagingItems<SearchViewData>,
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