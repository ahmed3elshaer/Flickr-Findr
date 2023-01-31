package com.search.presentation

import com.components.presentation.Action

sealed interface SearchAction : Action {
    data class SearchPhotos(val query: String) : SearchAction
    object GetSearchTerms : SearchAction
    object ClearResults : SearchAction
}

