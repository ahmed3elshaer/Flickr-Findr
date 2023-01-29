package com.search.data.remote

import com.search.data.PhotosRepositoryImpl.Companion.PageSize
import com.search.data.remote.model.SearchPhotoResponseApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchPhotosRemote(private val httpClient: HttpClient) {
    suspend fun searchPhotosByText(
        searchTerm: String,
        page: Int
    ): SearchPhotoResponseApi {
        return httpClient.get {
            parameter("method", "flickr.photos.search")
            parameter("text", searchTerm)
            parameter("page", page)
            parameter("per_page", PageSize)
        }.body()
    }
}
