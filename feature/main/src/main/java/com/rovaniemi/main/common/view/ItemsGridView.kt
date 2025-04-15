package com.rovaniemi.main.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rovaniemi.main.common.viewdata.SearchViewData
import com.rovaniemi.ui.util.DisableOverScroll

@Composable
internal fun ItemsGridView(
    modifier: Modifier = Modifier,
    navigationHeight: Dp,
    items: List<SearchViewData>,
    onClickItem: ((item: SearchViewData) -> Unit)? = null,
) {
    DisableOverScroll {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                )
                .padding(
                    horizontal = 4.dp,
                ),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                bottom = navigationHeight,
            ),
        ) {
            items(
                count = items.size,
                key = { index ->
                    items[index].hashCode()
                },
            ) { index ->
                SearchItemView(
                    viewData = items[index],
                    onClickUpdateBookmark = onClickItem,
                )
            }
        }
    }
}