package com.rovaniemi.data.repository

import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.model.domain.StorageItem
import com.rovaniemi.model.entity.StorageEntity
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val database: StorageDatabase,
) {
    suspend fun getAll(): List<StorageItem> {
        return database.storageDao().getAll().map {
            StorageItem(
                id = it.id,
                thumbnail = it.thumbnail,
                dateTime = it.dateTime,
                isBookmark = it.isBookmark,
            )
        }
    }

    suspend fun insertBookmark(item: StorageItem) {
        return database.storageDao().insertBookmark(
            StorageEntity(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = item.isBookmark,
            )
        )
    }

    suspend fun deleteBookmark(id: Long) {
        return database.storageDao().deleteBookmarkById(id)
    }
}