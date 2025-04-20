package com.rovaniemi.data.di

import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.data.repository.RoomRepositoryImpl
import com.rovaniemi.data.repository.SearchRepositoryImpl
import com.rovaniemi.data.room.StorageDatabase
import com.rovaniemi.data.room.dao.StorageDao
import com.rovaniemii.domain.repository.RoomRepository
import com.rovaniemii.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    internal fun provideSearchRepository(kakaoAPIService: KakaoAPIService): SearchRepository {
        return SearchRepositoryImpl(kakaoAPIService)
    }

    @Provides
    @Singleton
    internal fun provideRoomRepository(
        storageDao: StorageDao,
        storageDatabase: StorageDatabase,
    ): RoomRepository {
        return RoomRepositoryImpl(storageDao, storageDatabase)
    }
}