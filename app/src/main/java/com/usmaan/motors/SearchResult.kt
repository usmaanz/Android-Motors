package com.usmaan.motors

sealed class SearchResult {
    object Empty : SearchResult()
    object Error : SearchResult()
    class Success(val data: List<Motor>) : SearchResult()
    object Loading : SearchResult()
}
