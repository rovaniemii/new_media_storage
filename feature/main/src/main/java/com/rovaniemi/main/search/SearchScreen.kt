package com.rovaniemi.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rovaniemi.main.compose.view.ItemsGridView
import com.rovaniemi.main.compose.viewdata.SearchViewData
import com.rovaniemi.main.search.view.SearchErrorView
import com.rovaniemi.main.search.view.SearchLoadingView
import com.rovaniemi.main.search.view.SearchWordInputView
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    navigationHeight: Dp,
    isSearchInitialized: Boolean,
    query: String,
    items: LazyPagingItems<SearchViewData>,
    onSearchButtonClick: (query: String) -> Unit,
    onClickUpdateBookmark: (item: SearchViewData) -> Unit,
) {
    var searchQuery by remember { mutableStateOf(query) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            ),
    ) {
        SearchWordInputView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                ),
            initValue = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            onSearchButtonClick = {
                onSearchButtonClick(searchQuery)
            },
        )

        when (val loadState = items.loadState.refresh) {
            is LoadState.NotLoading -> {
                ItemsGridView(
                    navigationHeight = navigationHeight,
                    items = items,
                    onClickItem = onClickUpdateBookmark,
                )
            }

            is LoadState.Loading -> {
                if (!isSearchInitialized) {
                    SearchLoadingView()
                }
            }

            is LoadState.Error -> {
                SearchErrorView(
                    loadState = loadState,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSearchScreen() {
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

    SearchScreen(
        navigationHeight = 0.dp,
        isSearchInitialized = true,
        query = "부산",
        items = lazyItems,
        onSearchButtonClick = {},
        onClickUpdateBookmark = {}
    )
}