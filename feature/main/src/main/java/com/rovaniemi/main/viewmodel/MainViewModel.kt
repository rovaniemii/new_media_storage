package com.rovaniemi.main.viewmodel

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
import kotlinx.coroutines.flow.*
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
    val cachedQuery =_cachedQuery.asStateFlow()
    private val _bookmarkEventFlow = MutableSharedFlow<BookmarkEvent>()

    private val _isSearchInitialized = MutableStateFlow(false)
    val isSearchInitialized = _isSearchInitialized.asStateFlow()

    sealed class BookmarkEvent {
        data object InsertSuccess : BookmarkEvent()
        data object InsertFail : BookmarkEvent()
    }

    val searchPagingData = combine(
        _searchPagingData,
        _storageItems,
    ) { pagingData, storageList ->
        pagingData.map { item ->
            SearchViewData(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = storageList.any { it.id == item.id && it.isBookmark }
            )
        }
    }

    val storageItems = _storageItems.asStateFlow()

    init {
        _isSearchInitialized.value = true
        requestInitialStorage()
        observeBookmarkEvent()
    }

    fun updateSearchQuery(query: String) {
        if (_cachedQuery.value == query) return

        _isSearchInitialized.value = false
        _cachedQuery.value = query
        requestSearch(query)
    }

    fun updateBookmark(item: SearchViewData) {
        viewModelScope.launch {
            try {
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
                _bookmarkEventFlow.emit(BookmarkEvent.InsertSuccess)
            } catch (e: Exception) {
                _bookmarkEventFlow.emit(BookmarkEvent.InsertFail)
            }
        }
    }

    private fun requestInitialStorage() {
        viewModelScope.launch {
            _storageItems.value = roomRepository.getAll()
        }
    }

    private fun refreshStorage() {
        viewModelScope.launch {
            _storageItems.value = roomRepository.getAll()
        }
    }

    private fun observeBookmarkEvent() {
        viewModelScope.launch {
            _bookmarkEventFlow.collectLatest { event ->
                when (event) {
                    is BookmarkEvent.InsertSuccess -> refreshStorage()
                    is BookmarkEvent.InsertFail -> Unit
                }
            }
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