package com.search.domain.usecase.impl

import androidx.paging.PagingData
import com.search.domain.model.Photo
import com.search.domain.model.SearchTerm
import com.search.domain.repository.PhotosRepository
import com.search.domain.repository.SearchTermsRepository
import com.search.domain.usecase.api.SearchPhotosByText
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion

internal class SearchPhotosByTextImpl @Inject constructor(
    private val photosRepository: PhotosRepository,
    private val searchTermsRepository: SearchTermsRepository
) : SearchPhotosByText {

    override fun invoke(query: String): Flow<PagingData<Photo>> =
        photosRepository
            .searchPhotosByText(query)
            .onCompletion { searchTermsRepository.saveSearchTerm(SearchTerm(id = "0", query)) }
}
