package com.rovaniemi.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoDTO(
    val title: String = "",

    @Json(name = "play_time")
    val playTime: Int = 0,

    val thumbnail: String = "",
    val url: String = "",

    @Json(name = "datetime")
    val dateTime: String = "",

    val author: String = ""
)