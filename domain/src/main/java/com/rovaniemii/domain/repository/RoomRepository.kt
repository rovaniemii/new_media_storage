package com.rovaniemii.domain.repository

import androidx.paging.PagingData
import com.rovaniemii.domain.model.StorageItem
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getItemsPaged(): Flow<PagingData<StorageItem>>
    suspend fun getAll(): List<StorageItem>
    suspend fun insertBookmark(item: StorageItem)
    suspend fun deleteBookmark(id: Long)
}