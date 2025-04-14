package com.rovaniemi.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDTO(
    val collection: String = "",

    @field:Json(name = "thumbnail_url")
    val thumbnailUrl: String = "",

    @field:Json(name = "image_url")
    val imageUrl: String = "",

    val width: Int = 0,
    val height: Int = 0,

    @field:Json(name = "display_sitename")
    val displaySiteName: String = "",

    @field:Json(name = "doc_url")
    val docUrl: String = "",

    val dateTime: String = "",
)