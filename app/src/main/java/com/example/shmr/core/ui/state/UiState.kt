package com.example.shmr.core.ui.state

sealed interface UiState<out T> {
    object Loading: UiState<Nothing>

    data class Success<T>(val data: T): UiState<T>

    data class Error(val error: Throwable): UiState<Nothing>
}