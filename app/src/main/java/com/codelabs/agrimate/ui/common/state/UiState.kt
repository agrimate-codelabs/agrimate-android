package com.codelabs.agrimate.ui.common.state

sealed class UiState<out T: Any?> {
    object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}