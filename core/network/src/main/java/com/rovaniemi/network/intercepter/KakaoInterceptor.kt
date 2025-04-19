package com.rovaniemi.network.intercepter

import com.rovaniemi.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KakaoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(KAKAO_AUTHORIZATION_HEADER, BuildConfig.KAKAO_API_AUTH_KEY)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val KAKAO_AUTHORIZATION_HEADER = "Authorization"
    }
}