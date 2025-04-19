package com.rovaniemi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rovaniemi.data.room.dao.StorageDao
import com.rovaniemii.model_domain.StorageItem
import javax.inject.Inject

class RoomPagingSource @Inject constructor(
    private val dao: StorageDao,
) : PagingSource<Int, StorageItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StorageItem> {
        val page = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val items = dao.getItemsPaged(
                limit = pageSize,
                offset = page * pageSize,
            ).map {
                StorageItem(
                    id = it.id,
                    thumbnail = it.thumbnail,
                    dateTime = it.dateTime,
                    isBookmark = it.isBookmark,
                )
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StorageItem>): Int? {
        return null
    }
}