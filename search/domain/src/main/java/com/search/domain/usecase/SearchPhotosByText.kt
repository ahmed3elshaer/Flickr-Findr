package com.search.domain.usecase

import androidx.paging.PagingData
import com.search.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface SearchPhotosByText {
    suspend operator fun invoke(query: String): Flow<Flow<PagingData<Photo>>>
}
