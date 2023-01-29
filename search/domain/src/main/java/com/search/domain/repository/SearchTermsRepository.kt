package com.search.domain.repository

import com.search.domain.model.SearchTerm
import kotlinx.coroutines.flow.Flow

interface SearchTermsRepository {
    fun getAllSearchTerms(): Flow<List<SearchTerm>>
    suspend fun saveSearchTerm(searchTerm: SearchTerm)
    suspend fun clearAllSearchTerms()
    suspend fun clearSearchTerm(searchTerm: SearchTerm)
}
