package com.rovaniemi.model.dto

import com.google.gson.annotations.SerializedName

data class VideoDTO(
    val title: String = "",

    @SerializedName("play_time")
    val playTime: Int = 0,

    val thumbnail: String = "",
    val url: String = "",

    @SerializedName("datetime")
    val dateTime: String = "",

    val author: String = ""
)