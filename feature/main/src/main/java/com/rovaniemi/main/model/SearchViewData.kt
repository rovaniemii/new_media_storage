package com.rovaniemi.main.model

internal data class SearchViewData(
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val isBookmark: Boolean,
)