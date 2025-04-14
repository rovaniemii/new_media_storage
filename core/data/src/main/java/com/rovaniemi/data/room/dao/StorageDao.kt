package com.rovaniemi.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.rovaniemi.model.entity.StorageEntity

@Dao
interface StorageDao {

    @Query("SELECT * FROM StorageEntity ORDER BY createdAt DESC")
    suspend fun getAll(): List<StorageEntity>
}