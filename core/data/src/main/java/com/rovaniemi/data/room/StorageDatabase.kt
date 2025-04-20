package com.rovaniemi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rovaniemi.data.room.dao.StorageDao
import com.rovaniemi.model.entity.StorageEntity

@Database(
    entities = [StorageEntity::class],
    version = 1,
    exportSchema = false,
)
internal abstract class StorageDatabase : RoomDatabase() {
    internal abstract fun storageDao(): StorageDao
}