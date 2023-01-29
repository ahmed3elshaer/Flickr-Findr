package com.search.data.remote.model

import com.search.domain.model.Photo

fun SearchPhotoResponseApi.PhotosApi.PhotoApi.toDomain() = Photo(
    farm = farm,
    id = id,
    owner = owner,
    secret = secret,
    server = server,
    title = title
)
