package com.search.domain.usecase

import com.search.domain.model.SearchTerm
import kotlinx.coroutines.flow.Flow

interface GetAllSearchTerms {
    suspend operator fun invoke(): Flow<List<SearchTerm>>
}
