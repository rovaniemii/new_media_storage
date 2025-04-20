package com.rovaniemii.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class StorageEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo
    val thumbnail: String,

    @ColumnInfo
    val dateTime: String,

    @ColumnInfo
    val isBookmark: Boolean,

    @ColumnInfo
    var createdAt: Long = System.currentTimeMillis(),
)