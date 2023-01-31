package com.search.domain.usecase.impl

import com.search.domain.model.SearchTerm
import com.search.domain.repository.SearchTermsRepository
import com.search.domain.usecase.GetAllSearchTerms
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class GetAllSearchTermsImpl @Inject constructor(
    private val searchTermsRepository: SearchTermsRepository
) : GetAllSearchTerms {
    override suspend fun invoke(): Flow<List<SearchTerm>> {
        return searchTermsRepository.getAllSearchTerms()
    }
}
