package com.rovaniemi.data.api

import com.rovaniemi.network.constant.KakaoConstant
import com.rovaniemii.model_dto.dto.KakaoDTO
import com.rovaniemii.model_dto.response.ResponseDTO

import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoAPIService {
    @GET(KakaoConstant.KAKAO_IMAGE_URL)
    suspend fun searchImages(
        @Query("sort") sort: String = KakaoConstant.KAKAO_SORT_RECENCY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ResponseDTO<KakaoDTO.Image>

    @GET(KakaoConstant.KAKAO_VIDEO_URL)
    suspend fun searchVideos(
        @Query("sort") sort: String = KakaoConstant.KAKAO_SORT_RECENCY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ResponseDTO<KakaoDTO.Video>
}