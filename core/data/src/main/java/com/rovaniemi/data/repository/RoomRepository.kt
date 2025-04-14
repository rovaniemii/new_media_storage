package com.rovaniemi.data.repository

import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.model.domain.StorageItem
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val database: StorageDatabase,
) {
    suspend fun getAll(): List<StorageItem> {
        return database
            .storageDao()
            .getAll()
            .map {
                StorageItem(
                    id = it.id,
                    thumbnail = it.thumbnail,
                    dateTime = it.dateTime,
                    isBookmark = it.isBookmark,
                    createdAt = it.createdAt,
                )
            }
    }
}