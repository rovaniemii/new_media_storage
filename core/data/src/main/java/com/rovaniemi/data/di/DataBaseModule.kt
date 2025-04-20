package com.rovaniemi.data.di

import android.content.Context
import androidx.room.Room
import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.data.room.dao.StorageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataBaseModule {
    @Provides
    @Singleton
    internal fun provideStorageDatabase(
        @ApplicationContext context: Context,
    ): StorageDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = StorageDatabase::class.java,
            name = "StorageDatabase_db"
        ).build()
    }

    @Provides
    @Singleton
    internal fun provideStorageDao(storageDatabase: StorageDatabase): StorageDao {
        return storageDatabase.storageDao()
    }
}