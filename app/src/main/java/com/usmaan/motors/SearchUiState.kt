package com.usmaan.motors

/**
 * A class which is responsible for wrapping the response of an API request.
 *
 * Either the com.usmaan.motors.result is a [Success] or an [Error]
 */
sealed class SearchUiState<out T> {
    object Loading : SearchUiState<Nothing>()
    data class Success<out T>(val data: T) : SearchUiState<T>()
    data class Error(val error: String) : SearchUiState<Nothing>()
}