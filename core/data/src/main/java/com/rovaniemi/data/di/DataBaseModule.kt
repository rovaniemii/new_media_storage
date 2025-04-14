package com.rovaniemi.data.di

import android.content.Context
import androidx.room.Room
import com.rovaniemi.data.room.StorageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideStorageDatabase(
        context: Context,
    ): StorageDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = StorageDatabase::class.java,
            name = "StorageDatabase_db"
        ).build()
    }
}