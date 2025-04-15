package com.rovaniemi.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
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
import com.rovaniemi.main.model.ScreenType
import com.rovaniemi.main.search.SearchScreen
import com.rovaniemi.main.storage.StorageScreen
import com.rovaniemi.main.view.BottomNavigationBar
import com.rovaniemi.main.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val density = LocalDensity.current
    val navController = rememberNavController()

    val searchPagingData = viewModel.searchPagingData.collectAsLazyPagingItems()
    val storageData by viewModel.storageItems.collectAsState()
    val isSearchInitialized by viewModel.isSearchInitialized.collectAsState()

    var navigationHeight by remember { mutableStateOf(0.dp) }

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
                    items = searchPagingData,
                    onSearchButtonClick = viewModel::updateSearchQuery,
                    onClickUpdateBookmark = viewModel::updateBookmark,
                )
            }

            composable(
                route = ScreenType.BOOKMARKS,
            ) {
                StorageScreen(
                    items = storageData,
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