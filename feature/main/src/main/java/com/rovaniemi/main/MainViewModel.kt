package com.rovaniemi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rovaniemi.data.repository.RoomRepository
import com.rovaniemi.data.repository.SearchRepository
import com.rovaniemi.main.model.SearchViewData
import com.rovaniemi.model.domain.SearchItem
import com.rovaniemi.model.domain.StorageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val _searchPagingData = MutableStateFlow<PagingData<SearchItem>>(PagingData.empty())
    private val _storageItems = MutableStateFlow<List<StorageItem>>(emptyList())

    private val _cachedQuery = MutableStateFlow("")

    val searchPagingData = combine(
        _searchPagingData,
        _storageItems,
    ) { pagingData, storageList ->
        pagingData.map { item ->
            SearchViewData(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = storageList.find { it.id == item.id }?.isBookmark ?: false
            )
        }
    }
    val storageItems = _storageItems.asStateFlow()

    fun updateSearchQuery(query: String) {
        if (_cachedQuery.value == query) {
            return
        } else {
            _cachedQuery.value = query
            requestSearch(query)
        }
    }

    fun updateBookmark(item: SearchViewData) {
        viewModelScope.launch {
            if (item.isBookmark) {
                roomRepository.deleteBookmark(item.id)
            } else {
                roomRepository.insertBookmark(
                    StorageItem(
                        id = item.id,
                        thumbnail = item.thumbnail,
                        dateTime = item.dateTime,
                        isBookmark = true,
                    )
                )
            }
        }

        requestStorageItems()
    }

    private fun requestStorageItems() {
        viewModelScope.launch {
            _storageItems.value = roomRepository.getAll()
        }
    }

    private fun requestSearch(query: String) {
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