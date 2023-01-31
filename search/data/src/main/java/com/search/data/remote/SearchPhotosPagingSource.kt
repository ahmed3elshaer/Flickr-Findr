package com.search.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.search.data.remote.model.SearchPhotoResponseApi
import com.search.data.remote.model.toDomain
import com.search.domain.model.Photo

class SearchPhotosPagingSource(
    private val searchRemote: SearchPhotosRemote,
    private val searchTerm: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(
        state: PagingState<Int, Photo>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val response = searchRemote.searchPhotosByText(
                searchTerm = searchTerm,
                page = page
            )

            LoadResult.Page(
                data = response.photosWrapper.photos.map { it.toDomain() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.photosWrapper.photos.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
