package com.rovaniemi.data.di

import com.rovaniemi.data.api.KakaoAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object APIServiceModule {

    @Provides
    @Singleton
    internal fun provideKakaoService(retrofit: Retrofit): KakaoAPIService {
        return retrofit.create(KakaoAPIService::class.java)
    }
}