package com.usmaan.motors

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/search")
    suspend fun getAll() : SearchResults?

    @GET("/search")
    suspend fun searchBy(
        @Query("make") make: String,
        @Query("model") model: String,
        @Query("year") year: String
    ) : SearchResults?
}