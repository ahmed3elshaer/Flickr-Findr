package com.search.domain.repository

import androidx.paging.Pager
import com.search.domain.model.Photo
import javax.inject.Inject

interface PhotosRepository {
    fun searchPhotosByText(searchTerm: String): Pager<Int, Photo>
}
