package com.usmaan.motors

import java.lang.Exception

class SearchRepository(
    private val service: SearchService,
    private val mapper: SearchMapper = SearchMapper()
) {

    suspend fun getAll(
        onSuccess: (List<Motor>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = service.getAll()
            handleResponse(response, onSuccess, onError)
        } catch (ex: Exception) {
            onError("Failed to call API")
        }
    }

    suspend fun getSearch(
        make: String,
        model: String,
        year: String,
        onSuccess: (List<Motor>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = service.searchBy(make, model, year)
            handleResponse(response, onSuccess, onError)
        } catch (ex: Exception) {
            onError("Failed to call API")
        }
    }

    private fun handleResponse(
        response: SearchResults?,
        onSuccess: (List<Motor>) -> Unit,
        onError: (String) -> Unit
    ) {
        return if (response != null) {
            onSuccess(mapper(response))
        } else {
            onError("No Motors Returned")
        }
    }
}