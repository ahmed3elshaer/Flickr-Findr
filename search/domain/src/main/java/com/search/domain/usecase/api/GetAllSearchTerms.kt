package com.search.domain.usecase.api

import com.search.domain.model.SearchTerm
import kotlinx.coroutines.flow.Flow

interface GetAllSearchTerms {
    operator fun invoke(): Flow<List<SearchTerm>>
}
