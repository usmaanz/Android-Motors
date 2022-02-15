package com.usmaan.motors


/**
 * Maps the DTO [SearchResults] to what the UI model expects: [List<Motor>]
 */
class SearchMapper {
    operator fun invoke(from: SearchResults): List<Motor> =
        from.searchResults.map { it }
}