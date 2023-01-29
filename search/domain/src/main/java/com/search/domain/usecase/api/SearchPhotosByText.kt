package com.search.domain.usecase.api

import androidx.paging.PagingData
import com.search.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface SearchPhotosByText {
    operator fun invoke(query: String): Flow<PagingData<Photo>>
}
