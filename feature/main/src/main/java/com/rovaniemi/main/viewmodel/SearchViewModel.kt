package com.rovaniemi.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rovaniemi.data.repository.RoomRepository
import com.rovaniemi.data.repository.SearchRepository
import com.rovaniemi.main.compose.viewdata.SearchViewData
import com.rovaniemii.model_domain.StorageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val roomRepository: RoomRepository,
) : ViewModel() {
    sealed class BookmarkEvent {
        data object Success : BookmarkEvent()
        data class Fail(val message: String) : BookmarkEvent()
    }

    private val _searchPagingData =
        MutableStateFlow<PagingData<com.rovaniemii.model_domain.SearchItem>>(PagingData.empty())
    private val _storageItemsGetAll = MutableStateFlow<List<StorageItem>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _bookmarkEventFlow = MutableSharedFlow<BookmarkEvent>()
    val bookmarkEventFlow = _bookmarkEventFlow.asSharedFlow()

    private val _cachedQuery = MutableStateFlow("")
    private val cachedQuery = _cachedQuery.asStateFlow()
    val noNeedToLoading = cachedQuery.map { it.isNotBlank() }

    // https://proandroiddev.com/loading-initial-data-in-launchedeffect-vs-viewmodel-f1747c20ce62
    internal val searchPagingData = combine(
        _searchPagingData,
        _storageItemsGetAll,
    ) { pagingData, storageList ->
        pagingData.map { item ->
            SearchViewData(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = storageList.any { it.id == item.id && it.isBookmark }
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty(),
    )

    init {
        viewModelScope.launch {
            _storageItemsGetAll.value = roomRepository.getAll()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchButtonClick() {
        if (cachedQuery.value == searchQuery.value) return

        requestSearch(searchQuery.value)
    }

    private fun requestSearch(query: String) {
        _cachedQuery.value = query

        viewModelScope.launch {
            searchRepository
                .getSearchPagingSource(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _searchPagingData.value = pagingData
                }
        }
    }

    fun refreshStorage() {
        viewModelScope.launch {
            _storageItemsGetAll.value = roomRepository.getAll()
        }
    }

    internal fun updateBookmark(item: SearchViewData) {
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
                _bookmarkEventFlow.emit(BookmarkEvent.Success)
            } catch (e: Exception) {
                _bookmarkEventFlow.emit(BookmarkEvent.Fail("저장에 실패했습니다."))
            }
        }
    }
}