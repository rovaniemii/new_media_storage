package com.rovaniemi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.data.paging.SearchPagingSource
import com.rovaniemii.model_domain.SearchItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val kakaoAPIService: KakaoAPIService,
) {
    fun getSearchPagingSource(query: String): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    kakaoAPIService = kakaoAPIService,
                    query = query,
                )
            }
        ).flow
    }
}