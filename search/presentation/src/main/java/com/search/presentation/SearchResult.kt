package com.search.presentation

import androidx.paging.PagingData
import com.components.presentation.Result
import com.search.domain.model.Photo
import com.search.domain.model.SearchTerm

sealed interface SearchResult : Result {
    data class Error(val message: String?) : SearchResult
    object Idle : SearchResult
    object InFlight : SearchResult
    data class RecentSearchTerms(val searchTerms: List<SearchTerm>) : SearchResult
    data class Photos(val searchTerms: PagingData<Photo>) : SearchResult
}
