package com.rovaniemi.main.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rovaniemi.main.model.SearchViewData
import com.rovaniemi.ui.util.DisableOverScroll

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
            .fillMaxSize(),
    ) {
        SearchWordInputView(
            modifier = Modifier
                .fillMaxWidth(),
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
                DisableOverScroll {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(
                            bottom = navigationHeight,
                        ),
                    ) {
                        items(
                            count = items.itemCount,
                            key = { index ->
                                items[index].hashCode()
                            },
                        ) { index ->
                            items[index]?.let { item ->
                                SearchItemView(
                                    viewData = item,
                                    onClickUpdateBookmark = onClickUpdateBookmark,
                                )
                            }
                        }
                    }
                }
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