package com.rovaniemi.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rovaniemi.main.compose.view.BottomNavigationBar
import com.rovaniemi.main.compose.viewdata.ScreenType
import com.rovaniemi.main.search.SearchScreen
import com.rovaniemi.main.storage.StorageScreen
import com.rovaniemi.main.viewmodel.SearchViewModel
import com.rovaniemi.main.viewmodel.StorageViewModel
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(),
    storageViewModel: StorageViewModel = viewModel(),
    handleToastMessage: (message: String) -> Unit,
) {
    val density = LocalDensity.current
    val navController = rememberNavController()

    val searchPagingData = searchViewModel.searchPagingData.collectAsLazyPagingItems()
    val storagePagingData = storageViewModel.storagePagingData.collectAsLazyPagingItems()
    val isSearchInitialized by searchViewModel.isSearchInitialized.collectAsState()
    val cachedQuery by searchViewModel.cachedQuery.collectAsState()

    var navigationHeight by remember { mutableStateOf(0.dp) }

    LaunchedEffect(Unit) {
        launch {
            searchViewModel.bookmarkEventFlow.collect { event ->
                when (event) {
                    is SearchViewModel.BookmarkEvent.Success -> {
                        searchViewModel.refreshStorage()
                        storagePagingData.refresh()
                    }

                    is SearchViewModel.BookmarkEvent.Fail -> {
                        handleToastMessage(event.message)
                    }
                }
            }
        }

        launch {
            storageViewModel.bookmarkEventFlow.collect { event ->
                when (event) {
                    is StorageViewModel.BookmarkEvent.DeleteSuccess -> {
                        storagePagingData.refresh()
                    }

                    is StorageViewModel.BookmarkEvent.DeleteFail -> {
                        handleToastMessage(event.message)
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = ScreenType.SEARCH,
        ) {
            composable(
                route = ScreenType.SEARCH,
            ) {
                SearchScreen(
                    navigationHeight = navigationHeight,
                    isSearchInitialized = isSearchInitialized,
                    query = cachedQuery,
                    items = searchPagingData,
                    onSearchButtonClick = searchViewModel::updateSearchQuery,
                    onClickUpdateBookmark = searchViewModel::updateBookmark,
                )
            }

            composable(
                route = ScreenType.BOOKMARKS,
            ) {
                StorageScreen(
                    navigationHeight = navigationHeight,
                    items = storagePagingData,
                    deleteItem = storageViewModel::deleteBookmark,
                )
            }
        }

        BottomNavigationBar(
            modifier = Modifier
                .align(
                    alignment = Alignment.BottomCenter,
                )
                .onSizeChanged {
                    navigationHeight = with(density) { it.height.toDp() }
                },
            navController = navController,
            onClick = { screenRoute ->
                navController.navigate(screenRoute) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}