package com.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.search.data.remote.SearchPhotosPagingSource
import com.search.data.remote.SearchPhotosRemote
import com.search.data.remote.model.toDomain
import com.search.domain.model.Photo
import com.search.domain.repository.PhotosRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PhotosRepositoryImpl @Inject constructor(
    private val searchPhotosRemote: SearchPhotosRemote
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
    }

    companion object {
        const val PageSize = 25
    }
}
