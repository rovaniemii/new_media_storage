package com.rovaniemi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rovaniemi.data.api.KakaoAPIService
import com.rovaniemi.data.paging.SearchPagingSource
import com.rovaniemii.domain.model.SearchItem
import com.rovaniemii.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val kakaoAPIService: KakaoAPIService,
): SearchRepository {
    override fun getSearchPagingSource(query: String): Flow<PagingData<SearchItem>> {
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