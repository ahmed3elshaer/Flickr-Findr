package com.search.domain

import androidx.paging.PagingData
import com.search.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun searchPhotosByText(searchTerm: String): Flow<PagingData<Photo>>
    fun getAllSearchTerms(): Flow<List<String>>
}
