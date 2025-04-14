package com.rovaniemi.network.di

import com.rovaniemi.network.intercepter.KakaoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Singleton
    @Provides
    fun provideKakaoClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                retryOnConnectionFailure(true)
                addInterceptor(KakaoInterceptor())
                addInterceptor(HttpLoggingInterceptor())
            }
            .build()
    }
}