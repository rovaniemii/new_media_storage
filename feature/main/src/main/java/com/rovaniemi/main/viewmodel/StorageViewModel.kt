package com.rovaniemi.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rovaniemi.data.repository.RoomRepository
import com.rovaniemi.main.compose.viewdata.SearchViewData
import com.rovaniemii.model_domain.StorageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
) : ViewModel() {
    sealed class BookmarkEvent {
        data object DeleteSuccess : BookmarkEvent()
        data class DeleteFail(val message: String) : BookmarkEvent()
    }

    private val _refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val _bookmarkEventFlow = MutableSharedFlow<BookmarkEvent>()
    val bookmarkEventFlow = _bookmarkEventFlow.asSharedFlow()

    // https://proandroiddev.com/loading-initial-data-in-launchedeffect-vs-viewmodel-f1747c20ce62
    private val _storagePagingData = MutableStateFlow<PagingData<StorageItem>>(PagingData.empty())
    internal val storagePagingData = _storagePagingData.map { pagingData ->
        pagingData.map { item ->
            SearchViewData(
                id = item.id,
                thumbnail = item.thumbnail,
                dateTime = item.dateTime,
                isBookmark = true,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty(),
    )

    init {
        viewModelScope.launch {
            _refreshTrigger.emit(Unit)
        }

        viewModelScope.launch {
            roomRepository
                .getItemsPaged()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _storagePagingData.value = pagingData
                }
        }
    }

    fun deleteBookmark(id: Long) {
        viewModelScope.launch {
            try {
                roomRepository.deleteBookmark(id)
                _bookmarkEventFlow.emit(BookmarkEvent.DeleteSuccess)
            } catch (e: Exception) {
                _bookmarkEventFlow.emit(BookmarkEvent.DeleteFail("삭제에 실패했습니다."))
            }
        }
    }

    fun refreshStorage() {
        _refreshTrigger.tryEmit(Unit)
    }
}