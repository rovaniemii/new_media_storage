package com.rovaniemi.ui.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 연속 이벤트 중 첫 번째 것만 처리
 */
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}