package com.rovaniemi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rovaniemi.data.paging.RoomPagingSource
import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.data.room.dao.StorageDao
import com.rovaniemi.model.entity.StorageEntity
import com.rovaniemi.network.constant.KakaoConstant
import com.rovaniemii.domain.model.StorageItem
import com.rovaniemii.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RoomRepositoryImpl @Inject constructor(
    private val dao: StorageDao,
    private val database: StorageDatabase,
) : RoomRepository {
    override fun getItemsPaged(): Flow<PagingData<StorageItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = KakaoConstant.LOAD_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { RoomPagingSource(dao) },
        ).flow
    }

    override suspend fun getAll(): List<StorageItem> {
        return database.storageDao().getAll().map {
            StorageItem(
                id = it.id,
                thumbnail = it.thumbnail,
                dateTime = it.dateTime,
                isBookmark = it.isBookmark,
            )
        }
    }

    override suspend fun insertBookmark(item: StorageItem) {
        return database.storageDao().insertBookmark(
            StorageEntity(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = item.isBookmark,
            )
        )
    }

    override suspend fun deleteBookmark(id: Long) {
        return database.storageDao().deleteBookmarkById(id)
    }
}