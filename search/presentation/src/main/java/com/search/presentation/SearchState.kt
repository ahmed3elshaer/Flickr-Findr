package com.search.presentation

import androidx.paging.PagingData
import com.components.presentation.State
import com.search.domain.model.Photo
import com.search.domain.model.SearchTerm

sealed interface SearchState : State {
    data class Error(val message: String?) : SearchState
    object Loading : SearchState
    object Idle : SearchState
    data class RecentSearchTerms(val searchTerms: List<SearchTerm>) : SearchState
    data class Photos(val searchTerms: PagingData<Photo>) : SearchState
}
