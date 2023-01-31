package com.search.domain.usecase.impl

import androidx.paging.PagingData
import com.search.domain.model.Photo
import com.search.domain.model.SearchTerm
import com.search.domain.repository.PhotosRepository
import com.search.domain.repository.SearchTermsRepository
import com.search.domain.usecase.SearchPhotosByText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

internal class SearchPhotosByTextImpl @Inject constructor(
    private val photosRepository: PhotosRepository,
    private val searchTermsRepository: SearchTermsRepository
) : SearchPhotosByText {

    override suspend fun invoke(query: String): Flow<Flow<PagingData<Photo>>> =
        flowOf(
            photosRepository
                .searchPhotosByText(query).flow
        )
            .onCompletion {
                searchTermsRepository.saveSearchTerm(SearchTerm(id = "0", query))
            }
}

