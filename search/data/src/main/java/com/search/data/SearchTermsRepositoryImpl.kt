package com.search.data

import com.search.data.local.SearchTermDao
import com.search.data.local.model.SearchTermLocal
import com.search.domain.model.SearchTerm
import com.search.domain.repository.SearchTermsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchTermsRepositoryImpl @Inject constructor(
    private val searchTermDao: SearchTermDao
) : SearchTermsRepository {
    override suspend fun getAllSearchTerms(): Flow<List<SearchTerm>> {
        return withContext(Dispatchers.IO) {
            flowOf(searchTermDao.getAll())
                .map { searchTermsLocal ->
                    searchTermsLocal.map {
                        SearchTerm(
                            it.uid.toString(),
                            it.text
                        )
                    }
                }
        }
    }

    override suspend fun saveSearchTerm(searchTerm: SearchTerm) {
        return withContext(Dispatchers.IO) {
            searchTermDao.insertAll(SearchTermLocal(text = searchTerm.text))
        }
    }

    override suspend fun clearAllSearchTerms() {
        return withContext(Dispatchers.IO) { searchTermDao.deleteAll() }
    }

    override suspend fun clearSearchTerm(searchTerm: SearchTerm) {
        return withContext(Dispatchers.IO) {
            searchTermDao.delete(
                SearchTermLocal(
                    searchTerm.id.toInt(),
                    searchTerm.text
                )
            )
        }
    }
}
