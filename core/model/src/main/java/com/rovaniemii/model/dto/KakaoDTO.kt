package com.rovaniemii.model.dto

import com.google.gson.annotations.SerializedName

sealed class KakaoDTO {

    data class Image(
        val collection: String = "",

        @SerializedName("thumbnail_url")
        val thumbnailUrl: String = "",

        @SerializedName("image_url")
        val imageUrl: String = "",

        val width: Int = 0,
        val height: Int = 0,

        @SerializedName("display_sitename")
        val displaySiteName: String = "",

        @SerializedName("doc_url")
        val docUrl: String = "",

        @SerializedName("datetime")
        val dateTime: String = "",
    ) : KakaoDTO()


    data class Video(
        val title: String = "",

        @SerializedName("play_time")
        val playTime: Int = 0,

        val thumbnail: String = "",
        val url: String = "",

        @SerializedName("datetime")
        val dateTime: String = "",

        val author: String = "",
    ) : KakaoDTO()
}