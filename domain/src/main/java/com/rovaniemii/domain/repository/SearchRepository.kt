package com.rovaniemii.domain.repository

import androidx.paging.PagingData
import com.rovaniemii.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchPagingSource(query: String): Flow<PagingData<SearchItem>>
}