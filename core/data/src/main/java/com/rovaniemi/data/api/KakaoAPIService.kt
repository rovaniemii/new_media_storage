package com.rovaniemi.data.api

import com.rovaniemi.model.dto.ImageDTO
import com.rovaniemi.model.dto.VideoDTO
import com.rovaniemi.model.response.ResponseDTO
import com.rovaniemi.network.constant.KakaoConstant

import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoAPIService {
    @GET(KakaoConstant.KAKAO_IMAGE_URL)
    suspend fun searchImages(
        @Query("sort") sort: String = KakaoConstant.KAKAO_SORT_RECENCY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ResponseDTO<ImageDTO>

    @GET(KakaoConstant.KAKAO_VIDEO_URL)
    suspend fun searchVideos(
        @Query("sort") sort: String = KakaoConstant.KAKAO_SORT_RECENCY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ResponseDTO<VideoDTO>
}