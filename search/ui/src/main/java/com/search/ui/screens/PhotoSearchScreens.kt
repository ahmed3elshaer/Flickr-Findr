package com.search.ui.screens

import androidx.core.text.htmlEncode
import java.net.URLDecoder
import java.net.URLEncoder

sealed class PhotoSearchScreens(open val route: String) {
    object SearchResult : PhotoSearchScreens(route = "SearchResult")

    class PhotoDetails(
        title: String,
        url: String
    ) : PhotoSearchScreens(route = "PhotoDetails?$titleArg=${title.htmlEncode()}&$urlArg=$url") {
        override val route = super.route

        companion object {
            const val titleArg = "title"
            const val urlArg = "url"
            const val route = "PhotoDetails?$titleArg={$titleArg}&$urlArg={$urlArg}"
        }
    }
}

fun String.decode(): String = URLDecoder.decode(this, "UTF-8")
fun String.encode(): String = URLEncoder.encode(this, "UTF-8")