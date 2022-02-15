package com.usmaan.motors

import com.google.gson.annotations.SerializedName

data class SearchResults(@SerializedName("searchResults") val searchResults: List<Motor>)