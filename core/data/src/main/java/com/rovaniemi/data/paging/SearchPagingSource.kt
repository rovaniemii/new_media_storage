package com.rovaniemi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.model.domain.SearchItem
import java.text.SimpleDateFormat
import java.util.Locale

class SearchPagingSource(
    private val kakaoAPIService: KakaoAPIService,
    private val query: String,
) : PagingSource<Int, SearchItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val imageResponse = kakaoAPIService
                .searchImages(
                    query = query,
                    page = page,
                    size = pageSize,
                ).documents.map {
                    SearchItem(
                        id = it.thumbnailUrl.hashCode().toLong(),
                        thumbnail = it.thumbnailUrl,
                        dateTime = it.dateTime,
                    )
                }
            val videoResponse = kakaoAPIService
                .searchVideos(
                    query = query,
                    page = page,
                    size = pageSize,
                ).documents.map {
                    SearchItem(
                        id = it.thumbnail.hashCode().toLong(),
                        thumbnail = it.thumbnail,
                        dateTime = it.dateTime,
                    )
                }

            val responseResult = (imageResponse + videoResponse).sortedByDescendingDateTime()
            LoadResult.Page(
                data = responseResult,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseResult.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun List<SearchItem>.sortedByDescendingDateTime(): List<SearchItem> {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        return sortedByDescending {
            try {
                formatter.parse(it.dateTime)?.time ?: 0L
            } catch (e: Exception) {
                0L
            }
        }
    }
}