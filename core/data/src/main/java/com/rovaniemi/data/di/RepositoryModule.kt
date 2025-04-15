package com.rovaniemi.data.di

import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.data.repository.RoomRepository
import com.rovaniemi.data.repository.SearchRepository
import com.rovaniemi.data.room.StorageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(kakaoAPIService: KakaoAPIService): SearchRepository {
        return SearchRepository(kakaoAPIService)
    }

    @Provides
    @Singleton
    fun provideRoomRepository(
        storageDatabase: StorageDatabase
    ): RoomRepository {
        return RoomRepository(storageDatabase)
    }
}