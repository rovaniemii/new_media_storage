package com.rovaniemi.data.di

import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(kakaoAPIService: KakaoAPIService): SearchRepository {
        return SearchRepository(kakaoAPIService)
    }
}