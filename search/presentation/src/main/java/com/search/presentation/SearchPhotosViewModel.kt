package com.search.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.components.presentation.ViewModel
import com.search.domain.usecase.GetAllSearchTerms
import com.search.domain.usecase.SearchPhotosByText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(
    private val searchPhotosByText: SearchPhotosByText,
    private val getAllSearchTerms: GetAllSearchTerms
) : ViewModel<SearchAction, SearchResult, SearchState>(SearchState.Idle) {
    override suspend fun SearchAction.process(): Flow<SearchResult> {
        return when (this) {
            SearchAction.GetSearchTerms -> getAllSearchTerms()
                .map { SearchResult.RecentSearchTerms(it) }
                .filterIsInstance<SearchResult>()
                .catch { emit(SearchResult.Error(it.message)) }

            is SearchAction.SearchPhotos -> flowOf(query)
                .filter { it.isNotBlank() && it.length > 1 }
                .flatMapMerge { validatedQuery ->
                    searchPhotosByText(validatedQuery)
                        .map { SearchResult.Photos(it.cachedIn(viewModelScope)) }
                        .filterIsInstance<SearchResult>()
                        .onStart { emit(SearchResult.InFlight) }
                }
                .catch { emit(SearchResult.Error(it.message)) }
        }
    }

    override fun SearchState.reduce(result: SearchResult): SearchState {
        return when (result) {
            is SearchResult.Error -> SearchState.Error(result.message)
            SearchResult.Idle -> this
            SearchResult.InFlight -> SearchState.Loading
            is SearchResult.Photos -> SearchState.Photos(result.photos)
            is SearchResult.RecentSearchTerms -> SearchState.RecentSearchTerms(result.searchTerms)
        }
    }
}
