package com.rovaniemi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rovaniemi.model.entity.StorageEntity

@Dao
interface StorageDao {

    @Query("SELECT * FROM StorageEntity ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getItemsPaged(
        limit: Int,     // 행(row)의 개수 제한
        offset: Int,    // 몇 번째 행부터 가져올 지 설정
    ): List<StorageEntity>

    @Query("SELECT * FROM StorageEntity ORDER BY createdAt DESC")
    suspend fun getAll(): List<StorageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(item: StorageEntity)


    @Query("DELETE FROM StorageEntity WHERE id = :id")
    suspend fun deleteBookmarkById(id: Long)
}