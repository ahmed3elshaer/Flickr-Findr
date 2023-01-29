package com.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.search.data.local.SearchTermDao
import com.search.data.local.model.Term
import com.search.data.remote.SearchPhotosPagingSource
import com.search.data.remote.SearchPhotosRemote
import com.search.data.remote.model.toDomain
import com.search.domain.PhotosRepository
import com.search.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotosRepositoryImpl(
    private val searchPhotosRemote: SearchPhotosRemote,
    private val searchTermDao: SearchTermDao
) : PhotosRepository {
    override fun searchPhotosByText(searchTerm: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PageSize
            ),
            pagingSourceFactory = {
                SearchPhotosPagingSource(searchRemote = searchPhotosRemote, searchTerm = searchTerm)
            }
        ).flow.map { pagingData ->
            pagingData
                .map { photosApi ->
                    photosApi.toDomain()
                }
        }
            .also {
                searchTermDao.insertAll(Term(text = searchTerm))
            }
    }

    override fun getAllSearchTerms(): Flow<List<String>> {
        return searchTermDao.getAll()
            .map { it.map { it.text } }
    }

    companion object {
        const val PageSize = 25
    }
}
