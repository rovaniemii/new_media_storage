package com.rovaniemi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rovaniemi.data.paging.RoomPagingSource
import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.data.room.dao.StorageDao
import com.rovaniemi.model.entity.StorageEntity
import com.rovaniemii.model_domain.StorageItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val dao: StorageDao,
    private val database: StorageDatabase,
) {
    fun getItemsPaged(): Flow<PagingData<StorageItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { RoomPagingSource(dao) },
        ).flow
    }

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