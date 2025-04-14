package com.rovaniemi.model.domain

class StorageItem(
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val isBookmark: Boolean,
    var createdAt: Long,
)