package com.rovaniemi.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rovaniemi.model.entity.StorageEntity

@Dao
interface StorageDao {

    @Query("SELECT * FROM StorageEntity ORDER BY createdAt DESC")
    suspend fun getAll(): List<StorageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(item: StorageEntity)


    @Query("DELETE FROM StorageEntity WHERE id = :id")
    suspend fun deleteBookmarkById(id: Long)
}