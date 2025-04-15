package com.rovaniemi.main.storage

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rovaniemi.model.domain.StorageItem
import com.rovaniemi.ui.util.DisableOverScroll

@Composable
internal fun StorageScreen(
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

@Preview
@Composable
private fun PreviewStorageScreen(){
    StorageScreen(
        items = listOf(
            StorageItem(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
            StorageItem(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
            StorageItem(
                id = 0L,
                thumbnail = "",
                dateTime = "2025년 4월 8일",
                isBookmark = true,
            ),
        )
    )
}