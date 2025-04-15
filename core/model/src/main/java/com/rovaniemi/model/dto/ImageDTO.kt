package com.rovaniemi.model.dto

import com.google.gson.annotations.SerializedName

data class ImageDTO(
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
)