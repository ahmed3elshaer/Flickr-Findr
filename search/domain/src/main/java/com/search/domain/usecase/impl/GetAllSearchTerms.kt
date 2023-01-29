package com.search.domain.usecase.impl

import com.search.domain.model.SearchTerm
import com.search.domain.repository.SearchTermsRepository
import com.search.domain.usecase.api.GetAllSearchTerms
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class GetAllSearchTerms @Inject constructor(
    private val searchTermsRepository: SearchTermsRepository
) : GetAllSearchTerms {
    override fun invoke(): Flow<List<SearchTerm>> {
        return searchTermsRepository.getAllSearchTerms()
    }
}
