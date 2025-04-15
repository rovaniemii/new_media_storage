package com.rovaniemi.main.common.viewdata

internal data class SearchViewData(
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val isBookmark: Boolean,
)