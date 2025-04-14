package com.rovaniemi.data.di

import com.rovaniemi.data.api.KakaoAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object APIServiceModule {

    @Provides
    @Singleton
    fun provideKakaoService(retrofit: Retrofit): KakaoAPIService {
        return retrofit.create(KakaoAPIService::class.java)
    }
}