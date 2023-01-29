package com.search.data

import com.search.data.local.SearchTermDao
import com.search.data.local.model.SearchTermLocal
import com.search.domain.model.SearchTerm
import com.search.domain.repository.SearchTermsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchTermsRepositoryImpl @Inject constructor(
    private val searchTermDao: SearchTermDao
) : SearchTermsRepository {
    override fun getAllSearchTerms(): Flow<List<SearchTerm>> {
        return searchTermDao.getAll()
            .map { searchTermsLocal ->
                searchTermsLocal.map {
                    SearchTerm(
                        it.uid.toString(),
                        it.text
                    )
                }
            }
    }

    override suspend fun saveSearchTerm(searchTerm: SearchTerm) {
        searchTermDao.insertAll(SearchTermLocal(text = searchTerm.text))
    }

    override suspend fun clearAllSearchTerms() {
        searchTermDao.deleteAll()
    }

    override suspend fun clearSearchTerm(searchTerm: SearchTerm) {
        return searchTermDao.delete(
            SearchTermLocal(
                searchTerm.id.toInt(),
                searchTerm.text
            )
        )
    }
}
