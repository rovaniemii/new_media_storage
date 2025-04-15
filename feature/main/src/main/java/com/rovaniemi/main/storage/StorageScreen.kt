package com.rovaniemi.main.storage

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rovaniemi.model.domain.StorageItem
import com.rovaniemi.ui.util.DisableOverScroll

@Composable
fun StorageScreen(
    modifier: Modifier = Modifier,
    items: List<StorageItem>, // todo ViewData 생성
) {

    DisableOverScroll {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ) {
            items(
                count = items.size,
                key = { index ->
                    items[index].hashCode()
                },
            ) { index ->
                val item = items[index]

                StorageItemView(
                    viewData = item,
                )
            }
        }
    }
}