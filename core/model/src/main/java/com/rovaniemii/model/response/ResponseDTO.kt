package com.rovaniemii.model.response

import com.google.gson.annotations.SerializedName

data class ResponseDTO<T>(
    val meta: Meta,
    val documents: List<T>,
) {
    data class Meta(
        @SerializedName("total_count") val totalCount: Int = 0,
        @SerializedName("pageable_count") val pageableCount: Int = 0,
        @SerializedName("is_end") val isEnd: Boolean = true,
    )
}