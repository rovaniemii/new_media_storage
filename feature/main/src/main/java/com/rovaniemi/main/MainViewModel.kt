package com.rovaniemi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rovaniemi.data.repository.SearchRepository
import com.rovaniemi.model.domain.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _searchPagingData = MutableStateFlow<PagingData<SearchItem>>(PagingData.empty())

    fun requestSearch(query: String) {
        viewModelScope.launch {
            searchRepository
                .getSearchPagingSource(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _searchPagingData.value = pagingData
                }
        }
    }
}