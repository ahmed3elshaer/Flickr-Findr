package com.search.domain.model

data class Photo(
    val farm: Int,
    val id: String,
    val owner: String,
    val secret: String,
    val server: Int,
    val title: String
)

fun Photo.mediumUrl() =
    "https://farm$farm.staticflickr.com/$server/${id}_${secret}_c.jpg\n"

fun Photo.originalUrl() =
    "https://farm$farm.staticflickr.com/$server/${id}_${secret}_o.jpg\n"
