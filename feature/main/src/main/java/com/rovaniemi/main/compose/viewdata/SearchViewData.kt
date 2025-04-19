package com.rovaniemi.main.compose.viewdata

import androidx.compose.runtime.Stable

@Stable
internal data class SearchViewData(
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val isBookmark: Boolean,
)