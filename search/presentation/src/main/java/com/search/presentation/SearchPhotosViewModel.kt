package com.search.presentation

import com.components.presentation.ViewModel
import com.search.domain.usecase.api.GetAllSearchTerms
import com.search.domain.usecase.api.SearchPhotosByText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class SearchPhotosViewModel(
    private val searchPhotosByText: SearchPhotosByText,
    private val getAllSearchTerms: GetAllSearchTerms
) : ViewModel<SearchAction, SearchResult, SearchState>(SearchState.Idle) {
    override suspend fun SearchAction.process(): Flow<SearchResult> {
        return when (this) {
            SearchAction.GetSearchTerms -> getAllSearchTerms()
                .map { SearchResult.RecentSearchTerms(it) }
                .filterIsInstance<SearchResult>()
                .catch { emit(SearchResult.Error(it.message)) }

            is SearchAction.SearchPhotos -> searchPhotosByText(query)
                .map { SearchResult.Photos(it) }
                .filterIsInstance<SearchResult>()
                .catch { emit(SearchResult.Error(it.message)) }
                .onStart { emit(SearchResult.InFlight) }
        }
    }

    override fun SearchState.reduce(result: SearchResult): SearchState {
        return when (result) {
            is SearchResult.Error -> SearchState.Error(result.message)
            SearchResult.Idle -> this
            SearchResult.InFlight -> SearchState.Loading
            is SearchResult.Photos -> SearchState.Photos(result.searchTerms)
            is SearchResult.RecentSearchTerms -> SearchState.RecentSearchTerms(result.searchTerms)
        }
    }

}