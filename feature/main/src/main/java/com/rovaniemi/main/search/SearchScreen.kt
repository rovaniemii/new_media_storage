package com.rovaniemi.main.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rovaniemi.main.R
import com.rovaniemi.main.model.SearchViewData
import com.rovaniemi.ui.designsystem.loading.LottieAnimationView
import com.rovaniemi.ui.util.DisableOverScroll
import java.net.UnknownHostException

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
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        LottieAnimationView(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(
                                    size = 60.dp,
                                ),
                            jsonString = "rocket_loading.json",
                        )
                    }
                }
            }

            is LoadState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    val errorMessage = when (val error = (loadState as? LoadState.Error)?.error) {
                        is UnknownHostException -> stringResource(R.string.search_paging_network_message)
                        is IndexOutOfBoundsException -> stringResource(R.string.search_paging_no_search_message)
                        else -> error?.localizedMessage ?: stringResource(R.string.search_paging_unknown_error_message)
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}