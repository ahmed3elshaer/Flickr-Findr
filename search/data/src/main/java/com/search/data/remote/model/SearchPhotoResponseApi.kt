package com.search.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPhotoResponseApi(
    @SerialName("photos")
    val photosWrapper: PhotosApi = PhotosApi(),
    @SerialName("stat")
    val stat: String = ""
) {
    @Serializable
    data class PhotosApi(
        @SerialName("page")
        val page: Int = 0,
        @SerialName("pages")
        val pages: String = "",
        @SerialName("perpage")
        val perpage: Int = 0,
        @SerialName("photo")
        val photos: List<PhotoApi> = listOf(),
        @SerialName("total")
        val total: String = ""
    ) {
        @Serializable
        data class PhotoApi(
            @SerialName("farm")
            val farm: Int = 0,
            @SerialName("id")
            val id: String = "",
            @SerialName("isfamily")
            val isfamily: Int = 0,
            @SerialName("isfriend")
            val isfriend: Int = 0,
            @SerialName("ispublic")
            val ispublic: Int = 0,
            @SerialName("owner")
            val owner: String = "",
            @SerialName("secret")
            val secret: String = "",
            @SerialName("server")
            val server: Int = 0,
            @SerialName("title")
            val title: String = ""
        )
    }
}
