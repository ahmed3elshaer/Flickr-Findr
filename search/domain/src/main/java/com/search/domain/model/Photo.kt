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
    "https://farm$farm.staticflickr.com/$server/${id}_${secret}_t.jpg\n"

fun Photo.largeUrl() =
    "https://farm$farm.staticflickr.com/$server/${id}_${secret}_b.jpg\n".also { println(it) }
