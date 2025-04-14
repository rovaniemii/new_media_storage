package com.rovaniemi.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDTO<T>(
    val meta: Meta,
    val documents: List<T>,
) {
    @JsonClass(generateAdapter = true)
    data class Meta(
        @field:Json(name = "total_count") val totalCount: Int = 0,
        @field:Json(name = "pageable_count") val pageableCount: Int = 0,
        @field:Json(name = "is_end") val isEnd: Boolean = true,
    )
}